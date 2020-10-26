package com.pg.huliena.dsl.func;

import com.pg.huliena.dsl.bo.TasksDTO;

import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/26 6:47 下午
 */
public interface CalcImpl {
    Map<String,Object> calc(Map<String,Object> maps, TasksDTO tasksDTO);
}
