package com.example.common.result;

import com.example.common.exception.ErrorCode;

public class ResultUtil<T> {
    public static <T> Result<T> ok(T t) {
        return new Result<T>(0,t,"ok");
    }
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<T>(errorCode.getCode(),null,errorCode.getMessage());
    }
    public static <T> Result<T> fail(ErrorCode errorCode, String msg) {
        return new Result<T>(errorCode.getCode(),null,msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<T>(code,null,msg);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static Result error(int code, String message) {
        return new Result(code, (Object) null, message);
    }

    public static Result error(ErrorCode errorCode) {
        return new Result(errorCode.getCode(), (Object) null, errorCode.getMessage());
    }

    public static Result ok() {
        return new Result(0, (Object)null,"ok");
    }
}
