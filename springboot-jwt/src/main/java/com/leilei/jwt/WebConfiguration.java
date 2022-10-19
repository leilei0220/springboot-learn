package com.leilei.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 19:35
 * @desc web配置 使拦截器生效
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 添加；拦截器 以及拦截 或者放行规则
     * @param registry
     */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //将自定义的拦截器添加进去 必须是使用方法获取，不可直接New 否则该拦截器中中无法注入Bean
    registry.addInterceptor(getJwtInterceptor())
        //拦截所有
        .addPathPatterns("/**")
        //排除路径  排除中的路径 不需要进行拦截
        .excludePathPatterns("/login/**");
  }

  /**
   * 定义方法获取自定义的 JWTInterceptor
   * @return
   */
  @Bean
  public JwtInterceptor getJwtInterceptor() {
    return new JwtInterceptor();
  }

}
