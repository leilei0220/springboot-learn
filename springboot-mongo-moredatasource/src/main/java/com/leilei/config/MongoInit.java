package com.leilei.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author : leilei
 * @date : 16:01 2020/2/16
 * @desc :  mongo连接配置类
 */
@Configuration
public class MongoInit {
    @Bean(name = "oneMongoProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.data.mongodb.one")
    public MongoProperties statisMongoProperties() {
        System.out.println("-------------------- oneMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "twoMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.two")
    public MongoProperties twoMongoProperties() {
        System.out.println("-------------------- twoMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "threeMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.three")
    public MongoProperties threeMongoProperties() {
        System.out.println("-------------------- threeMongoProperties init ---------------------");
        return new MongoProperties();
    }
}
