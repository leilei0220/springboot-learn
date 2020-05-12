package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootFileQiniuApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootFileQiniuApplication.class, args);
  }

}
