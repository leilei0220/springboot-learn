package com.leilei;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.leilei.mapper", "com.leilei.chunk.mapper", "com.leilei.preSigned.mapper", "com.leilei.file.mapper"})
public class SpringbootStart {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStart.class, args);
    }

}
