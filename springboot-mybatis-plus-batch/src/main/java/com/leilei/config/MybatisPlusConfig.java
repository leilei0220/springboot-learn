package com.leilei.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @create 2021-09-09 10:56
 * @desc 配置
 **/
@Configuration
@MapperScan(basePackages = "com.leilei.mapper")
public class MybatisPlusConfig {

    /**
     * 将自定义SQl注入器 注册为BEAN
     * @return
     */
    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }
}
