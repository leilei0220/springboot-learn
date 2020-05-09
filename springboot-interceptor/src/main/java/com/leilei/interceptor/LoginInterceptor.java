package com.leilei.interceptor;

import com.alibaba.fastjson.JSON;
import com.leilei.annotation.NotNeedLogin;
import com.leilei.entity.vo.TokenConstant;
import com.leilei.redis.RedisUtil;
import com.leilei.utils.ajax.JsonReturn;
import com.leilei.utils.ajax.ServerCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lei
 * @date 2020/5/8 20:16
 * @desc 定义登录拦截器  alt+insert 选择需要复写的方法
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private RedisUtil redisUtil;

  /**
   * 进入方法前进行拦截
   *
   * @param request
   * @param response
   * @param handler
   * @return true 放行 进入到下一个拦截器（如果还有的话）,false 进行拦截
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    /**首先判断当前是否是请求控制器（controller,不是则直接放行*/
    if (handler instanceof HandlerMethod) {
      //获取到当前请求控制器 判断控制器方法中 是否含有注解
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      if (handlerMethod.getMethod().getAnnotation(NotNeedLogin.class) != null) {
        //注解存在 则放行
        return true;
      }
      /**获取当前请求接口路径 判断是否是请求swagger接口文档之类的 结尾*/
      String url = request.getRequestURI();
      if (url.endsWith("/swagger") || url.startsWith("/swagger-ui.html") || url
          .startsWith("/v2/api-docs")) {
        return true;
      }
      /**以上两者都不满足 则是无注解 且访问需要拦截的控制器情况下  当前需要登录,鉴定访问的Token*/
      //获取传来的token
      String authToken = request.getHeader(TokenConstant.getHeader());
      if (StringUtils.isBlank(authToken) || StringUtils.isBlank((String) redisUtil.get(authToken))) {
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        log.error("当前接口-{}需要登录。但尚未获取token，直接返回", url);
        response.getWriter().print(
            JSON.toJSON(JsonReturn.buildFailure("当前请求需要登录", ServerCode.EXPIRE)).toString());
        return false;
      }
    }
    return true;
  }

}
