package com.pg.huliena.dsl.executor;

import com.pg.huliena.dsl.bo.FlowTask;
import com.pg.huliena.dsl.bo.TasksDTO;
import com.pg.huliena.dsl.bo.WorkTask;
import com.pg.huliena.dsl.func.CalcImpl;
import com.pg.huliena.dsl.func.impl.TimeFuncImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static cn.hutool.core.lang.Singleton.put;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/23 4:20 下午
 */
@Slf4j
public class SimpleExecutor {


    public WorkTask execute(FlowTask flowTask, Map<String, Object> context) {
        WorkTask workTask = new WorkTask();
        workTask.setLock(WorkTask.WorkTaskStatus.RUNNING);
        workTask.getInputs().putAll(context);
        workTask.getRuntimeParam().putAll(context);
        Map<String, TasksDTO> tasks = flowTask.getTasks().parallelStream().collect(Collectors.toMap(
                task -> task.getAlias(),
                task -> task
        ));
        workTask.setTasks(tasks);

        Map<String, WorkTask.WorkTaskStatus> status = flowTask.getTasks()
                .parallelStream()
                .collect(Collectors.toConcurrentMap(
                        task -> task.getAlias(),
                        task -> WorkTask.WorkTaskStatus.INIT
                ));
        workTask.setStatus(status);
        // 解析
        Map<String, List<String>> dependency = flowTask.getTasks()
                .parallelStream()
                .collect(Collectors.toMap(
                        task -> task.getAlias(),
                        task -> task.getDependency()
                ));
        workTask.setDagStatus(dependency);

        doExector(workTask);
        return workTask;
    }

    private void doExector(WorkTask workTask) {
        if (checkWorkFlow(workTask)) {
            return;
        }
        List<String> keys = convertNowDag(workTask);
        realDoExec(keys, workTask);

    }

    public static Map<String, CalcImpl> cacheFunc = new HashMap<String, CalcImpl>

    {
        {
            put("LocalFunc", new TimeFuncImpl());
        }
    }

    ;

    private void realDoExec(List<String> tasks, WorkTask workTask) {
        Map<String, Object> runtimeParam = workTask.getRuntimeParam();
        tasks.parallelStream()
                .forEach(s -> {
                    TasksDTO tasksDTO = workTask.getTasks().get(s);
                    workTask.getStatus().put(tasksDTO.getAlias(), WorkTask.WorkTaskStatus.RUNNING);
                    try {
                        if ("LOCALFUNC".equals(tasksDTO.getTaskType())) {
                            CalcImpl calc = cacheFunc.get(tasksDTO.getMethod());
                            Map<String, Object> result = calc.calc(runtimeParam, tasksDTO);
                            workTask.getRuntimeParam().put(tasksDTO.getAlias(), result);

                        } else {
                            //其他类型
                        }
//                        String asd = CompletableFuture.supplyAsync(() -> {
//                            System.out.println("asd");
//
//                        }).get(
//                                tasksDTO.getTimeout(), TimeUnit.MILLISECONDS
//                        );
                        // 参数装载
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                        log.info"taskerror ");
                    } finally {
                        workTask.getStatus().put(s, WorkTask.WorkTaskStatus.COMPLETED);
                    }
                });
        doExector(workTask);

    }

    private List<String> convertNowDag(WorkTask workTask) {
        return workTask.getDagStatus().entrySet().parallelStream()
                .map(task -> {
                    String key = task.getKey();
                    int size = task.getValue()
                            .parallelStream()
                            .map(s -> workTask.getStatus().getOrDefault(key, WorkTask.WorkTaskStatus.COMPLETED) == WorkTask.WorkTaskStatus.COMPLETED
                            )
                            .filter(b -> !b)
                            .collect(Collectors.toList()).size();
                    if (size == 0 && workTask.getStatus().get(key) == WorkTask.WorkTaskStatus.INIT) {
                        return key;
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    /**
     * 任务终止
     *
     * @param workTask
     * @return
     */
    private boolean checkWorkFlow(WorkTask workTask) {
        return workTask.getLock() == WorkTask.WorkTaskStatus.COMPLETED ||
                workTask.getStatus().entrySet()
                        .parallelStream()
                        .map(s -> s.getValue() == WorkTask.WorkTaskStatus.COMPLETED)
                        .filter(b -> !b)
                        .collect(Collectors.toList()).size()
                        == 0;
    }
}
