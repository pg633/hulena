package com.pg.huliena.dsl;

import cn.hutool.core.io.file.FileReader;
import com.google.common.collect.Maps;
import com.pg.huliena.dsl.executor.SimpleExecutor;
import com.pg.huliena.dsl.bo.FlowTask;
import com.pg.huliena.dsl.parse.WorkFlowParse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;


/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/23 2:15 下午
 */
@Slf4j
public class Main {
    public static void main(String[] args)  {
        String path = "a.json";
        FileReader fileReader = new FileReader(path);
        String result = fileReader.readString();


        FlowTask parse = WorkFlowParse.parse(result);
        System.out.println(parse);
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        simpleExecutor.execute(parse, Maps.newHashMap());



//        WorkFlowParse.WorkFlowDef workFlowDef = WorkFlowParse.parse(resource);
//        InputStream inputStream = resource.getInputStream();
//        String s = inputStream.toString();
//
//        System.out.println(s);

//        log.info(workFlowDef.toString());
//        System.out.println(result);

//
//        CompletableFuture.supplyAsync(() -> login())
//                .thenApplyAsync(token -> userInfo(token))
//                .get();



    }
}
