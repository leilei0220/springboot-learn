package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootBatchCollapserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBatchCollapserApplication.class, args);
    }

}
