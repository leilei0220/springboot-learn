package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author leilei
 * desc EnableTransactionManagement 开启事务管理器
 * exclude = {DataSourceAutoConfiguration.class 排除springboot 一启动就自动寻找 数据源并设为单数据源
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootMybatisAtomikosMoredatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisAtomikosMoredatasourceApplication.class, args);
    }

}
