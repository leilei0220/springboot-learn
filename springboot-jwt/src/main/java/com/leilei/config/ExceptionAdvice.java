package com.leilei.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.leilei.common.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lei
 * @create 2022-10-19 14:59
 * @desc 全局异常拦截
 **/
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(TokenExpiredException.class)
    public AjaxResult tokenExpiredException() {
        return AjaxResult.error("token已过期", -2);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public AjaxResult jwtVerificationException() {
        return AjaxResult.error("token解析异常", -3);
    }

}
