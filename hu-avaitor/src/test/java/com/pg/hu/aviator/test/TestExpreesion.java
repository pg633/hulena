package com.pg.hu.aviator.test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.pg.hu.aviator.vo.AviRes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static com.pg.hu.aviator.util.AviUtil.executeExpression;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/21 1:53 下午
 */
@DisplayName("测试表达式执行")
public class TestExpreesion {
    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
    }
    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
    }


    @DisplayName("执行表达式")
    @Test
    void testFirstTest() {
        String expression = "a-(b-c)>100";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        AviRes<Boolean> booleanAviRes = executeExpression(expression, env, Boolean.class);
        Boolean value = booleanAviRes.getValue();
        Assertions.assertEquals(value,false);
    }


}
