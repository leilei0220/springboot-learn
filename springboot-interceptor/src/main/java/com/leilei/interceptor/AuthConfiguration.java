package com.leilei.interceptor;

import com.leilei.annotation.LoginUserParamAnylysis;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lei
 * @date 2020/5/8 19:44
 * @desc web的配置都可以在这类里面编写
 */
@Configuration
public class AuthConfiguration implements WebMvcConfigurer {

    /**
     * 添加；拦截器 以及拦截 或者放行规则
     * @param registry
     */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //将自定义的登录拦截器添加进去 必须是使用方法获取 否则LoginInterceptor中无法注入Bean
    registry.addInterceptor(getLoginInterceptor())
        //拦截所有
        .addPathPatterns("/**")
        //排除路径  排除中的路径 不需要进行拦截
        .excludePathPatterns("/login/**", "/register", "/static/**", "/templates/**");
  }

  /**
   * 复写自定义参数的方法 我定义的等路注解 @LoginUserParam 参数解析
   *
   * @param argumentResolvers
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(getLoginUserParamAnylysis());
  }


  @Bean
  public LoginInterceptor getLoginInterceptor() {
    return new LoginInterceptor();
  }

  @Bean
  public LoginUserParamAnylysis getLoginUserParamAnylysis() {
    return new LoginUserParamAnylysis();
  }


}
