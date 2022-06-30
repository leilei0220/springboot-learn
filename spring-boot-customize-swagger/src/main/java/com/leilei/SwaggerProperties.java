package com.leilei;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author lei
 * @create 2022-04-24 16:28
 * @desc 企业swagger属性封装
 **/
@Data
@EnableConfigurationProperties(SwaggerProperties.class)
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "project.swagger";


    @Value("${project.swagger.enable:true}")
    private boolean enable;


    @Value("${project.swagger.title:API接口Doc}")
    private String title;


    @Value("${project.swagger.desc:测试API接口文档}")
    private String desc;


    @Value("${project.swagger.version:1.0}")
    private String version;


    @Value("${project.swagger.basePackage:com.leilei}")
    private String basePackage;
}
