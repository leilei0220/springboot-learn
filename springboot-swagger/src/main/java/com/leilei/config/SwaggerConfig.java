package com.leilei.config;

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
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : leilei
 * @date : 21:53 2020/3/8
 * @desc : swagger配置类
 */

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.leilei"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalOperationParameters());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试Swagger")
                .description("系统注册、角色分配")
                .contact(new Contact("",null,null))
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }


    public List<Parameter> globalOperationParameters() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization")
                .description("TOKEN")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("83785daaa1e24ff3b67868d023825987")
                .required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
