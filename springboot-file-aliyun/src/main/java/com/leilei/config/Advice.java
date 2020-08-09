package com.leilei.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/9 12:46
 * @desc
 */
@RestControllerAdvice
public class Advice {
    @ExceptionHandler(CommonException.class)
    public Result feignException(CommonException exception) {
        return Result.fail(exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result feignException(Exception exception) {
        return Result.fail(exception.getMessage());
    }
}
