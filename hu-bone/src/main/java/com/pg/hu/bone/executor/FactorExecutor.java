package com.pg.hu.bone.executor;

import com.pg.hu.bone.executor.vo.FactorResult;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/14 1:43 下午
 */
public interface FactorExecutor {
    @Data
    class RuleVo {
        String realName;
        String dependency;
        String rules;
        String realType;
        String conType;
        int level;
    }
    Map<String, FactorResult> execute(List<RuleVo> factorRules , Map<String,Object> context );
}
