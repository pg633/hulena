package com.pg.huliena.dsl.bo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/23 3:59 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowTask {
    private String name;
    private String description;
    private List<TasksDTO> tasks;
    private Map<String, String> outputs;


}
