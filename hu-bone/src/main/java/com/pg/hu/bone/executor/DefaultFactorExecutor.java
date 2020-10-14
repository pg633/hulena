package com.pg.hu.bone.executor;

import com.google.common.collect.Maps;
import com.pg.hu.aviator.util.AviUtil;
import com.pg.hu.aviator.util.VarType;
import com.pg.hu.aviator.vo.AviRes;
import com.pg.hu.bone.executor.FactorExecutor;
import com.pg.hu.bone.executor.vo.FactorResult;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/10/14 1:54 下午
 */
public class DefaultFactorExecutor implements FactorExecutor {
    @Override
    public Map<String, FactorResult> execute(List<RuleVo> factorRules, Map<String, Object> context) {
        return doExecute(factorRules, context);
    }



    private Map<String, FactorResult> doExecute(List<RuleVo> factorRules, Map<String, Object> context) {
        //1 获取依赖层级关系
        Map<Integer, List<RuleVo>> rulevel =
                factorRules.stream().collect(Collectors.groupingBy(RuleVo::getLevel, Collectors.toList()));
        //2 根据层级以来关系计算相关表达式
        rulevel.entrySet().stream()
                .forEach(ruleList -> {
                    ruleList.getValue().parallelStream().forEach(
                            // 执行规则
                            rule ->  {
                                Object obj = doExecuteAvi(rule, context);
                                if(!Objects.isNull(obj)){
                                    context.put(rule.getRealName(),obj);
                                }
                            });

                });
        return Maps.newHashMap();
    }

    private Object doExecuteAvi(RuleVo rule, Map<String, Object> context) {
        AviRes aviRes = AviUtil.execAviExpress(rule.getRules(), context, getTypeClass(rule.getRealType()));
        if(aviRes.getCode() == AviRes.Code.SUCCESS){
            return transType(aviRes.getMsg(),rule.getConType());
        }else{
            return null;
        }
    }

    /**
     * 转换实际参数
     * @param msg
     * @param conType
     * @return
     */
    private Object transType(String msg, String conType) {
        return VarType.toDestType(msg,getTypeClass(conType));
    }

    /**
     * hashcode 类型
     * @param realType
     * @return
     */
    private Class getTypeClass(String realType) {
        if(realType.equals("str")){
            return String.class;
        }else if(realType.equals("int")){
            return Integer.class;
        }else{
            return String.class;
        }

    }
}
