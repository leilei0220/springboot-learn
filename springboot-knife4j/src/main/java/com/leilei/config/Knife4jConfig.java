package com.leilei.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.leilei.controller"))
                .paths(PathSelectors.any())
                .build()
                //设置全局参数，我们可以在这里设置jwt等
                .globalOperationParameters(globalOperationParameters());
    }
    public List<Parameter> globalOperationParameters() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization")
                .description("JWT 令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("1234561321asdasdas")
                .required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("某某项目后端API接口文档")
                .description("计算模块")
                .termsOfServiceUrl("localhost:8080")
                .contact(new Contact("panghu","https://blog.csdn.net/leilei1366615","xxx@qq.com"))
                .version("1.0.0")
                .build();
    }
}
