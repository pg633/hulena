package com.pg.hu.aviator.vo;

import com.google.common.base.Strings;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author lianzheng04
 * @version 1.0
 * @date 2020/9/17 7:15 下午
 */
@Data
@Builder
@ToString
public class AviRes<T> {
    Code code;
    T value;
    String msg;

    enum Code {
        SUCCESS, COMPILE_ERROR, EXECUTE_ERROR, DATETYPE_ERROR, CAST_ERROR
    }

    public static <T> AviRes<T> execteERROR(String msg) {
        if (Strings.isNullOrEmpty(msg)) {
            msg = "execute error";
        }
        if (msg.toLowerCase().contains("could not compare")) {
            return new AviRes<T>(Code.DATETYPE_ERROR, null, msg);
        }
        return new AviRes<T>(Code.EXECUTE_ERROR, null, msg);
    }

    public static <T> AviRes<T> compileError() {
        return new AviRes<T>(Code.COMPILE_ERROR, null, "compile error");
    }

    public static <T> AviRes<T> castError() {
        return new AviRes<T>(Code.CAST_ERROR, null, "cast error");
    }

    public static <T> AviRes<T> success(T value) {
        return new AviRes<T>(Code.SUCCESS, value, "success");
    }

    public static <T> AviRes<T> compileERROR() {
        return new AviRes<T>(Code.COMPILE_ERROR, null, "compile error");
    }
}

