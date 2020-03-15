package com.leilei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @author lei
 * @version 1.0
 * @date 2020/02/16 11:22
 * @desc: 监听芒果 保存数据 不配置此类  保存对象时候 回家对象类型作为一个字段保存下来
 */
@Configuration
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  MongoTemplate mongoTemplate;
  
  private static final String TYPEKEY = "_class";

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    MongoConverter converter = mongoTemplate.getConverter();
    if (converter.getTypeMapper().isTypeKey(TYPEKEY)) {
      ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
    }
  }
}