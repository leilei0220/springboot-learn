package com.leilei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.leilei.mapper")
@EnableCaching
public class SpringbootJ2CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJ2CacheApplication.class, args);
    }

}
