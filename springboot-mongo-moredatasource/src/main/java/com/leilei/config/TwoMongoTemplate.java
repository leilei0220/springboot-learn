package com.leilei.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author : leilei
 * @date : 16:04 2020/2/16
 * @desc : mongo第二个数据源
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.leilei.entity.two",
        mongoTemplateRef = "twoMongo")
public class TwoMongoTemplate {

    @Autowired
    @Qualifier("twoMongoProperties")
    private MongoProperties mongoProperties;

    @Bean(name = "twoMongo")//第二个数据源名字oneMongo
    public MongoTemplate listTemplate() throws Exception {
        return new MongoTemplate(listFactory(this.mongoProperties));
    }

    @Bean
    public MongoDbFactory listFactory(MongoProperties mongoProperties) throws Exception {

        return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties.getUri()));
    }
}

