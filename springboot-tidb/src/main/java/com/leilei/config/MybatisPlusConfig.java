package com.leilei.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lei
 * @date: 2024-07-16 15:13
 * @desc: mp配置
 */
@Configuration
@MapperScan("com.leilei")
public class MybatisPlusConfig {


    /**
     * mp拦截器
     * @author lei
     * @date 2024-07-16 15:19:10
     * @param
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor  mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }
}
