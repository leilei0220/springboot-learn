package com.leilei.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

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

  /**
   * 配置统一的事务管理器
   * @param ds1
   * @param ds2
   * @param ds3
   * @return
   */
  @Bean(name = "chainedTransactionManager")
  public ChainedTransactionManager transactionManager(
      @Qualifier("oneTransactionManager") PlatformTransactionManager ds1,
      @Qualifier("twoTransactionManager") PlatformTransactionManager ds2,
      @Qualifier("threeTransactionManager") PlatformTransactionManager ds3) {
    return new ChainedTransactionManager(ds1, ds2, ds3);
  }
}
