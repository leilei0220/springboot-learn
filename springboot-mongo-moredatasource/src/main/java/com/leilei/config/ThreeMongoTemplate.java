package com.leilei.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author : leilei
 * @date : 16:05 2020/2/16
 * @desc :mongo第三个数据源
 */
@Configuration
@EnableMongoRepositories(
    basePackages = "com.leilei.entity.three",
    mongoTemplateRef = "threeMongo")
public class ThreeMongoTemplate {

  @Autowired
  @Qualifier("threeMongoProperties")
  private MongoProperties mongoProperties;

  @Bean(name = "threeMongo")//第三个数据源名字oneMongo
  public MongoTemplate threeTemplate() throws Exception {
    return new MongoTemplate(threeFactory(this.mongoProperties));
  }

  @Bean
  public MongoDbFactory threeFactory(MongoProperties mongoProperties) throws Exception {

    return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties.getUri()));
  }

  @Bean(name = "threeTransactionManager")
  MongoTransactionManager threeFactoryTransactionManager() throws Exception {
    MongoDbFactory mongoDbFactory = threeFactory(this.mongoProperties);
    return new MongoTransactionManager(mongoDbFactory);
  }
}

