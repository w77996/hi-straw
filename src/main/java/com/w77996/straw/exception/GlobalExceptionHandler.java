package com.w77996.straw.exception;

import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局exception
 * @Author straw
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局错误拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    private Result<Object> exceptionHandler(Exception e) {
        log.error("enter exceptionHandler");
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCode());
        }
        return Result.error(ResultCode.ERROR.getCode(),e.getMessage());
    }
}
