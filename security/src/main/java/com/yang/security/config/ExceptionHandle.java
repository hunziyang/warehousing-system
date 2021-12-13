package com.yang.security.config;

import com.yang.security.result.Result;
import com.yang.security.result.ResultCode;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ExceptionHandle {

    /**
     * 无权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result exceptionHandler(Exception e) {
        return Result.error(ResultCode.NO_PERMISSION);
    }


}
