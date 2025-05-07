package com.leilei;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@MapperScan("com.leilei.mapper")
public class SpringbootStart {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStart.class, args);
    }

}
