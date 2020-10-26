package com.pg.huliena.dsl.parse;

import cn.hutool.json.JSONUtil;
import com.pg.huliena.dsl.bo.FlowTask;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/23 2:18 下午
 */
public class WorkFlowParse {

    public static FlowTask parse(String resource) {
        return JSONUtil.toBean(resource, FlowTask.class);
    }

}
