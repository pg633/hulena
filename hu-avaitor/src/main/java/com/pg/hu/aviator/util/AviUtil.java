package com.pg.hu.aviator.util;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.exception.CompileExpressionErrorException;
import com.googlecode.aviator.exception.ExpressionRuntimeException;
import com.pg.hu.aviator.func.AddFunction;
import com.pg.hu.aviator.vo.AviRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/17 7:36 下午
 */
@Slf4j
public class AviUtil {
    // 函数注册
    static {
        AviatorEvaluator.addFunction(new AddFunction());
        // balabala
    }

    // 变量名称不允许
    public static final Set<String> AVIATOR_KEYWORDS = Sets.newHashSet("true", "nil", "null", "false", "+", "-", "*", "/");

    /**
     * 不能 XJB 初始化
     */
    private AviUtil() {
    }


    // 过滤相关规则
    private final static Pattern PATTERN_BACKSLASH_N = Pattern.compile("\\\\n");
    private final static Pattern PATTERN_BACKSLASH_B = Pattern.compile("\\\\b");
    private final static Pattern PATTERN_BACKSLASH_T = Pattern.compile("\\\\t");
    private final static Pattern PATTERN_BACKSLASH_F = Pattern.compile("\\\\f");
    private final static Pattern PATTERN_BACKSLASH_R = Pattern.compile("\\\\r");

    private static String transInput(String input) {
        if (Strings.isNullOrEmpty(input)) {
            return input;
        }
        String output = input;
        if (input.contains("\\n")) {
            output = PATTERN_BACKSLASH_N.matcher(output).replaceAll("\\n");
        }
        if (input.contains("\\b")) {
            output = PATTERN_BACKSLASH_B.matcher(output).replaceAll("\\b");
        }
        if (input.contains("\\t")) {
            output = PATTERN_BACKSLASH_T.matcher(output).replaceAll("\t");
        }
        if (input.contains("\\f")) {
            output = PATTERN_BACKSLASH_F.matcher(output).replaceAll("\f");
        }
        if (input.contains("\\r")) {
            output = PATTERN_BACKSLASH_R.matcher(output).replaceAll("\r");
        }
        return output;
    }

    public static Expression getCompileExpression(String expStr) {
        return getCompileExpression(expStr, false);
    }

    /**
     * 获取编译选项
     *
     * @param expStr
     * @param throwError
     * @return
     */
    public static Expression getCompileExpression(String expStr, boolean throwError) {
        Expression res = null;

        try {
            expStr = transInput(expStr);
            res = AviatorEvaluator.compile(expStr, true);
        } catch (CompileExpressionErrorException e) {
            if (throwError) {
                throw new IllegalArgumentException("表达式语法有误 " + e.getMessage());
            } else {
                return null;
            }
        }
        if (res == null) {
            log.warn("#Avi copile error with msg {}", expStr);
        }
        return res;
    }

    /**
     * 获取有效的名称
     *
     * @param exp
     * @return
     */
    public static List<String> getValidParamNames(Expression exp) {
        List<String> res = Lists.newLinkedList();
        return exp.getVariableNames()
                .stream()
                .filter(s -> !AVIATOR_KEYWORDS.contains(s) && !s.contains("$"))
                .collect(Collectors.toList());

    }

    public static List<String> getValidParamNames(String expStr) {
        return getValidParamNames(expStr, false);
    }

    public static List<String> getValidParamNames(String expStr, boolean throwException) {
        if (Strings.isNullOrEmpty(expStr)) {
            return Lists.newArrayList();
        }
        Expression expCompiled = getCompileExpression(expStr, throwException);
        return getValidParamNames(expCompiled);
    }

    /**
     * 执行表达式
     *
     * @param expStr
     * @param param
     * @param type
     * @param <T>
     * @return
     */
    public static <T> AviRes<T> execAviExpress(String expStr, Map<String, Object> param, Class<T> type) {
        // 编译失败
        Expression compileExpression = getCompileExpression(expStr);
        if (compileExpression == null) {
            return AviRes.compileERROR();
        }
        // 转换 请求参数
        Map<String, Object> realMap = transQueryMap(param);

        Object res = null;
        try {
            res = compileExpression.execute(realMap);
        } catch (ExpressionRuntimeException e) {
            return AviRes.execteERROR(e.getMessage());
        } catch (Exception e) {
            log.error("##AVI EXECUTE exp {} ,msg {} ", expStr, e.getMessage());
            return AviRes.execteERROR(e.getMessage());
        }
        if (Objects.isNull(res)) {
            return AviRes.success(null);
        }


        tryDynasticCast(res, type);
        return null;
    }

    /**
     * c++ 动态类型转换
     * 因为表达式内会使用 int/float -> long/double 导致精度溢出 此时对于金融 业务会是一个大问题
     *
     * @param obj
     * @param cls
     * @param <T>
     */
    private static <T> T tryDynasticCast(Object obj, Class<T> cls) {
        Class src = cls.getClass();
        if (cls.equals(Object.class) || cls.equals(src)) {
            return (T) obj;
        }
        try {
            return VarType.toDestType(obj, cls);
        } catch (Exception e) {
            log.warn("tryDynasticCast obj:{}, destType:{} ", obj.toString(), cls.toString());
            return null;
        }
    }

    private static Map<String, Object> transQueryMap(Map<String, Object> param) {
        return param.entrySet().parallelStream()
                .map(kv -> {
                    Object value = kv.getValue();
                    if (value instanceof String) {
                        value = transInput((String) value);
                    }
                    return new ImmutablePair<String, Object>(kv.getKey(), value);
                }).collect(Collectors.toMap(
                        ImmutablePair::getLeft,
                        ImmutablePair::getRight,
                        (k, v) -> v
                ));
    }


    public static AviRes<Boolean> executeExpressionAsBool(String exp, Map<String, Object> params) {
        return executeExpression(exp, params, Boolean.class);
    }

    public static <T> AviRes<T> executeExpression(String exp, Map<String, Object> params, Class<T> type ) {
        Expression expCompiled = getCompileExpression(exp);
        if (expCompiled == null) {
            return AviRes.compileError();
        }


        params = transQueryMap(params);
        Object result = null;
        try {
            result = expCompiled.execute(params);
        } catch (ExpressionRuntimeException e) {
            return AviRes.execteERROR(e.getMessage());
        } catch (Exception e) {
            log.error("EXECUTE_EXPRESSION_ERROR exp:{}, msg:{}", exp, e.getMessage() );
            return AviRes.execteERROR(e.getMessage());
        }
        if (result == null) {
            return AviRes.success(null);
        }
        T castResult = tryDynasticCast(result, type);
        if (castResult == null) {
            return AviRes.castError();
        }
        return AviRes.success(castResult);
    }
}
