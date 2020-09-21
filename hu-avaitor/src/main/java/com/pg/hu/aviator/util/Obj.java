package com.pg.hu.aviator.util;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 抄的 不是手写的
 */
public class Obj {
    private static Logger LOGGER = LoggerFactory.getLogger(Obj.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");

    @Deprecated
    public static Long obj2Long(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }

        String str = obj.toString();
        try {
            double d = Double.valueOf(str);
            long l = Long.valueOf(DECIMAL_FORMAT.format(d));
            return l;
        } catch (Exception e) {
            LOGGER.warn("obj to long exception, obj:{}, msg:{}", str, e.getMessage());
            return null;
        }
    }

    public static Optional<Long> tryToParseObjectToLong(Object obj) {
        return Optional.fromNullable(obj2Long(obj));
    }

    @Deprecated
    public static <T> List<Long> objects2Longs(Collection<T> objects) {
        return parseObjectsToLongs(objects);
    }

    public static <T> List<Long> parseObjectsToLongs(Collection<T> objects) {
        if (objects == null) {
            return null;
        }

        List<Long> result = new ArrayList<Long>();
        for (Object obj : objects) {
            result.add(obj2Long(obj));
        }
        return result;
    }

    public static <T> List<Integer> objectsToInts(Collection<T> objects) {
        if (objects == null) {
            return null;
        }
        List<Integer> result = Lists.newArrayList();
        for (Object obj : objects) {
            result.add(obj2Int(obj));
        }
        return result;
    }

    @Deprecated
    public static Integer obj2Int(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        try {
            String str = obj.toString();
            double d = Double.valueOf(str);
            return Integer.valueOf(DECIMAL_FORMAT.format(d));
        } catch (Exception e) {
            LOGGER.warn("obj to int exception, obj:{}, msg:{}", obj, e.getMessage());
            return null;
        }
    }

    public static Optional<Integer> tryToParseObjectToInt(Object obj) {
        return Optional.fromNullable(obj2Int(obj));
    }

    @Deprecated
    public static Double obj2Double(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str = obj.toString();
            Double d = Double.valueOf(str);
            return d;
        } catch (Exception e) {
            LOGGER.warn("obj to double exception, obj:{}, msg:{}", obj, e.getMessage());
            return null;
        }
    }

    public static Optional<Double> tryToParseObjectToDouble(Object obj) {
        return Optional.fromNullable(obj2Double(obj));
    }

    @Deprecated
    public static Float obj2Float(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String str = obj.toString();
            Float f = Float.valueOf(str);
            return f;
        } catch (Exception e) {
            LOGGER.warn("obj to float exception, obj:{}, msg:{}", obj.toString(), e.getMessage());
            return null;
        }
    }

    public static Optional<Float> tryToParseObjToFloat(Object obj) {
        return Optional.fromNullable(obj2Float(obj));
    }

    public static String obj2String(Object obj) {
        return (null == obj) ? null : obj.toString();
    }

    public static Optional<Boolean> tryToParseObjectToBoolean(Object obj) {
        if (obj == null) {
            return Optional.absent();
        }
        if (obj instanceof Boolean) {
            return Optional.of((Boolean) obj);
        }
        Integer intValue = Obj.obj2Int(obj);
        if (intValue != null) {
            if (intValue == 1) {
                return Optional.of(true);
            } else {
                return Optional.of(false);
            }
        }
        String objStrLower = obj.toString().toLowerCase();
        if (obj instanceof String) {
            if ("true".equals(objStrLower)) {
                return Optional.of(true);
            } else if ("false".equals(objStrLower)) {
                return Optional.of(false);
            } else {
                return Optional.absent();
            }
        }
        return Optional.absent();
    }

    public static Object transformItemsDoubleToLong(Object obj) {
        if (obj instanceof List) {
            List objList = (List) obj;
            List retList = (List) obj;
            for (int index = 0; index < objList.size(); index++) {
                Object value = transformItemsDoubleToLong(objList.get(index));
                retList.set(index, value);
            }
            return retList;
        }
        if (obj instanceof Map) {
            Map retMap = (Map) obj;
            Iterator iter = ((Map) obj).entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = transformItemsDoubleToLong(entry.getValue());
                retMap.put(entry.getKey(), value);
            }
            return retMap;
        }
        if (obj instanceof Double) {
            obj = obj2Long(obj);
        }
        return obj;
    }

    @Deprecated
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return null;
        } catch (Exception e) {
            LOGGER.warn("to json str error. data:{}, msg:{}", obj, e.getMessage());
            return null;
        }
    }

    public static Optional<String> tryToTransformToJsonString(Object obj) {
        return Optional.fromNullable(toJsonString(obj));
    }

    @Deprecated
    public static <T> T fromJsonString(String jsonString, Class<T> type) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return null;
        } catch (Exception e) {
            LOGGER.warn("json to data error. data:{}, msg:{}", jsonString, e.getMessage());
            return null;
        }
    }

    public static <T> Optional<T> tryToParseJsonString(String jsonString, Class<T> type) {
        return Optional.fromNullable(fromJsonString(jsonString, type));
    }

    @Deprecated
    public static Integer objectToNonnegativeInteger(Object obj) {
        if (obj == null) {
            return null;
        }
        String str = obj.toString();

        return null;
    }

    public static Optional<Integer> tryToParseObjectToNonnegativeInteger(Object obj) {
        return Optional.fromNullable(objectToNonnegativeInteger(obj));
    }

    public static boolean equals(Object a, Object b){
        Preconditions.checkState(a.getClass().equals(b.getClass()), "function equals() is using between different classes objects");
        return Objects.equal(a, b);
    }

    //The Implementation of Math.toIntExact in JDK 8
    public static int tryLongToInt(long value) throws ArithmeticException {
        if ((int)value != value) {
            throw new ArithmeticException("integer overflow");
        }
        return (int)value;
    }

    public static <T> Optional<T> fromMap(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return Optional.absent();
        }
        try {
            Object obj = beanClass.newInstance();
            BeanUtils.populate(obj, map);
            return Optional.of((T) obj);
        } catch (Exception e) {
            return Optional.absent();
        }
    }

    // 浅拷贝
    public static <K, V> Map<K, V> copyOfMap(Map<K, V> context) {
        return Maps.newHashMap(context);
    }

    /**
     * Obj.obj2Long(obj)内部由于先将obj转换成double类型再转换成Long有精度损失
     * 故重写一个obj到Long的无损失的转换
     * @param obj
     * @return
     */
    public static Long accurateToLong(Object obj){
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }

        String str = obj.toString();
        try {
            BigDecimal d = toBigDecimal(obj);
            long l = Long.valueOf(new DecimalFormat("0").format(d));
            return l;
        } catch (Exception e) {
            LOGGER.info("OBJ_TO_LONG_EXCEPTION, obj:{}, msg:{}", str, e.getMessage());
            return null;
        }

    }

    public static BigDecimal toBigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        String str = obj2String(obj);
        if (Strings.isNullOrEmpty(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    public static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static List<Object> toList(Object obj) {
        if (obj == null) {
            return Lists.newArrayList();
        }
        if (obj instanceof List) {
            return (List<Object>) obj;
        }
        if (obj instanceof Set) {
            return Lists.newArrayList((Set<Object>) obj);
        }
        String str = obj.toString();
        try {
            return null;
        } catch (Exception e) {
            LOGGER.warn("TO_LIST_FAILED str: {}", str, e);
        }
        return Lists.newArrayList();
    }

    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return Maps.newHashMap();
        }
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        String str = obj.toString();
        try {
            return null;
        } catch (Exception e) {
            LOGGER.warn("TO_MAP_FAILED str: {}", str, e);
        }
        return Maps.newHashMap();
    }

    /**
     * 将json字符串转换为对应的collection<>
     *
     * @param jsonString
     * @param collectionClass
     * @param elementClass
     * @param <T>
     * @return
     */
    public static <T extends Collection> Optional<T> toCollection(String jsonString, Class<T> collectionClass, Class<?> elementClass) {
        try {
            return  null;
        } catch (Exception e) {
            return Optional.absent();
        }
    }

}
