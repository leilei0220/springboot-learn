package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author leilei
 */
@SpringBootApplication
@EnableSwagger2
public class SpringbootSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSwaggerApplication.class, args);
    }

}
