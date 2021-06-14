package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author lei
 * @version 1.0
 * @date 2021/6/14 15:11
 * @desc 官网配置 sql server 详细示例 https://debezium.io/documentation/reference/1.5/connectors/sqlserver.html
 * @desc 官网配置 mysql 详细示例https://debezium.io/documentation/reference/1.5/connectors/mysql.html
 */
@SpringBootApplication
public class SpringbootDebeziumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDebeziumApplication.class, args);
    }

}
