package com.pg.hu.bone.executor.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/14 1:44 下午
 */
@Data
@ToString
public class FactorResult {
    private Object value;
    private String code;
    private String msg;
    private Map<String,Object> accumulateDetail;
}
