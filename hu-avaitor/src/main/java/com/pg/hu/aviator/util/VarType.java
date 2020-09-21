package com.pg.hu.aviator.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/21 11:24 上午
 */
@Getter
@AllArgsConstructor
public enum VarType {
    BOOLEAN(0, Boolean.class),
    INTEGER(1, Integer.class),
    LONG(2, Long.class),
    FLOAT(3, Float.class),
    DOUBLE(4, Double.class),
    STRING(5, String.class),
    BIG_DECIMAL(6, BigDecimal.class),
    LIST(7, List.class),
    MAP(8, Map.class);
    private int code;
    private Class type;


    public static <T> T toDestType(Object obj, Class<T> clazz) {
        Optional<VarType> type = VarType.valueOf(clazz);
        if (type.isEmpty()) {
            return null;
        }
        return toDestType(obj, type.get());
    }

    /**
     * 实际转换类型
     *
     * @param object
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T toDestType(Object object, VarType type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case INTEGER:
                return (T) Obj.obj2Int(object);
            case LONG:
                return (T) Obj.accurateToLong(object);
            case FLOAT:
                return (T) Obj.obj2Float(object);
            case DOUBLE:
                return (T) Obj.obj2Double(object);
            case BOOLEAN:
                return (T) Obj.tryToParseObjectToBoolean(object).orNull();
            case STRING:
                return (T) Obj.obj2String(object);
            case BIG_DECIMAL:
                return (T) Obj.toBigDecimal(object);
            case LIST:
                return (T) Obj.toList(object);
            case MAP:
                return (T) Obj.toMap(object);
            default:
                break;
        }
        return null;
    }

    private static Optional<VarType> valueOf(Class clz) {
        return Arrays.stream(VarType.values()).filter(s -> s.getType().equals(clz)).findFirst();
    }
}
