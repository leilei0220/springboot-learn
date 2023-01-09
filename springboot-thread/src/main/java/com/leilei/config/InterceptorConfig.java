package com.leilei.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author lei
 * @create 2023-01-09 17:14
 * @desc 自定义拦截器
 **/
@Slf4j
public class InterceptorConfig implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String s = UUID.randomUUID().toString();
        log.info("主线程名：{}请求操作开始，添加主线程数据:{}", Thread.currentThread().getName(), s);
        ThreadContext.add(s);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadContext.remove();
        String name = Thread.currentThread().getName();
        log.info("主线程名：{}请求操作结束", name);
    }

}
