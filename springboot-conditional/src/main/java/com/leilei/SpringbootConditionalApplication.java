package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootConditionalApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(SpringbootConditionalApplication.class, args);
        String[] beanNames =  context.getBeanDefinitionNames();
        System.out.println("项目装配BEAN数量："+beanNames.length);
        for(String bean:beanNames){
            System.out.println(bean);
        }
    }

}
