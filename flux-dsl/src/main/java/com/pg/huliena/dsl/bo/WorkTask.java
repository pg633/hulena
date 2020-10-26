package com.pg.huliena.dsl.bo;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/23 4:21 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkTask {
    FlowTask flowTask;
    private Map<String, Object> inputs = Maps.newConcurrentMap();
    private Map<String, Object> runtimeParam = Maps.newConcurrentMap();
    private Map<String, Object> outputs = Maps.newHashMap();
    private WorkTaskStatus lock  ;
    private Map<String, WorkTaskStatus> status = Maps.newHashMap();
    private Map<String, TasksDTO> tasks = Maps.newHashMap();

    private Map<String, List<String>> dagStatus = Maps.newConcurrentMap();
   public enum WorkTaskStatus {
       INIT,
        RUNNING,
        COMPLETED
    }

}
