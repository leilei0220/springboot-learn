package com.leilei;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
public class SpringbootStart {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStart.class, args);
    }

}
