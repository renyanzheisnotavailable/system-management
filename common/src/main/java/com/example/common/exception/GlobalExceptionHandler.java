package com.example.common.exception;


import com.example.common.result.Result;
import com.example.common.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 *

 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtil.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 表单请求校验结果处理
     * @param bindException
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public Result<?> errorHandler(BindException bindException) {

        return ResultUtil.error( ErrorCode.PARAMS_ERROR.getCode(),bindException.getMessage());
    }

    /**
     * JSON请求校验结果，也就是请求中对实体标记了@RequestBody
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<?> errorHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResultUtil.error( ErrorCode.PARAMS_ERROR.getCode(),methodArgumentNotValidException.getMessage());
    }

/**
 * JSON请求校验结果，也就是请求中对实体标记了@RequestBody
 * @param methodArgumentNotValidException
 * @return
 */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<?> errorHandler(ConstraintViolationException constraintViolationException) {
        return ResultUtil.error( ErrorCode.PARAMS_ERROR.getCode(),constraintViolationException.getMessage());
    }
}


