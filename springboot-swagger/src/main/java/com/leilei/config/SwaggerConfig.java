package com.leilei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author : leilei
 * @date : 21:53 2020/3/8
 * @desc : swagger配置类
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //表示需要生成文档的范围 （哪个包下）
                .apis(RequestHandlerSelectors.basePackage("com.leilei.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private List<Parameter> buildOperationParameters(String token) {
        List<Parameter> parameters = Lists.newArrayList();
        parameters.add(new ParameterBuilder()
                .name("Authorization")
                .description("TOKEN")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue(token)
                .build());

        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("springboot-learn 学习小营地之整合swager接口文档工具")
                .termsOfServiceUrl("https://blog.csdn.net/leilei1366615")
                .version("1.0")
                .build();
    }

}
