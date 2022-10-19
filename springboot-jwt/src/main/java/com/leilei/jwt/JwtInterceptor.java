package com.leilei.jwt;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leilei.common.AjaxResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 18:35
 * @desc
 */
@Component
@Log4j2
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtSupport jwtSupport;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String url = request.getRequestURI();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccPermission annotation = handlerMethod.getMethod().getAnnotation(AccPermission.class);
            if (annotation == null) {
                annotation = handlerMethod.getMethodAnnotation(AccPermission.class);
                //无校验注解，则仍默认校验JWT合法性
                if (annotation == null) {
                    return true;
                    // return checkJWT(request,response,url);
                }
            }
            //如果设置了jwt=false，则直接放行
            if (!annotation.jwt()) {
                return true;
            }
            //jwt=true，则开始校验
            return checkJwt(request, response, url);
        }
        return true;
    }

    /**
     * 校验JWT
     *
     * @param request
     * @param response
     * @param url
     * @return
     * @throws Exception
     */
    public boolean checkJwt(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        String authToken = request.getHeader("Authorization");
        if (StringUtils.isBlank(authToken)) {
            return checkError(response, url);
        }
        jwtSupport.verify(authToken);
        return true;
    }

    /**
     * 校验失败
     *
     * @param response
     * @param url
     * @return
     * @throws Exception
     */
    public boolean checkError(HttpServletResponse response, String url) throws Exception {
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        log.error("当前接口-{}需要登录!", url);
        response.getWriter().print(JSON.toJSONString(AjaxResult.error("Token校验失败，当前请求需要登录！！！", 401)));
        return false;
    }
}
