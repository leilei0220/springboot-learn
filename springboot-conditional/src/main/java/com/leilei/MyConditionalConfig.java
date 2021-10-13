package com.leilei;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @create 2021-10-13 14:34
 * @desc Conditional 自定义装配条件选择器进行加载bean 条件选择器最后结果为true时 装配条件生效
 **/
@Configuration
public class MyConditionalConfig {

    @Bean
    @Conditional(value = MyConditionSelector.class)
    public User initUser() {
        return new User();
    }
}
