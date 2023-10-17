package com.example.common.exception;


/**
 * 抛异常工具类
 *
 */
public class ThrowUtils {



    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIfNot(boolean condition, ErrorCode errorCode) {
        throwIf(!condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }


    public static void checkUpdate(boolean condition) {
        throwIf(!condition, new BusinessException(ErrorCode.OPERATION_ERROR));
    }

    /**
     * 条件成立则抛异常
     *
     * @param Object
     * @param errorCode
     */
    public static void throwIfNull(Object Object, ErrorCode errorCode) {
        throwIf(Object == null, new BusinessException(errorCode));
    }
}
