package com.leilei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @author : leilei
 * @date : 16:00 2020/2/16
 * @desc : mongo监听 新增时消除默认添加的 _class 字段保存实体类类型
 */
@Configuration
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("oneMongo")
    MongoTemplate oneMongoTemplate;

    @Autowired
    @Qualifier("twoMongo")
    MongoTemplate twoMongoTemplate;

    @Autowired
    @Qualifier("threeMongo")
    MongoTemplate threeMongoTemplate;

    private static final String TYPEKEY = "_class";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        MongoConverter converter = oneMongoTemplate.getConverter();
        if (converter.getTypeMapper().isTypeKey(TYPEKEY)) {
            ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        MongoConverter converter2 = twoMongoTemplate.getConverter();
        if (converter2.getTypeMapper().isTypeKey(TYPEKEY)) {
            ((MappingMongoConverter) converter2).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        MongoConverter converter3 = threeMongoTemplate.getConverter();
        if (converter3.getTypeMapper().isTypeKey(TYPEKEY)) {
            ((MappingMongoConverter) converter3).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
    }
}
