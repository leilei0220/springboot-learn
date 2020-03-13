package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootQrcodeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootQrcodeApplication.class, args);
  }

}
