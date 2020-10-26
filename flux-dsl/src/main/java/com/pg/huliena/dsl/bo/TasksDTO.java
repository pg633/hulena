package com.pg.huliena.dsl.bo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/26 6:37 下午
 */

@NoArgsConstructor
@Data
public class TasksDTO {
    private String url;
    private String alias;
    private String taskType;
    private String method;
    private Integer timeout;
    private Map<String, Object> inputs;
    private Map<String, String> inputsExtra;
    private List<String> dependency = Lists.newArrayList();
}
