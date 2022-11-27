package com.leilei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.leilei.mapper")
public class SpringbootEventListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEventListenerApplication.class, args);
    }

}
