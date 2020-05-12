package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author leilei
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringbootMongoMoredatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongoMoredatasourceApplication.class, args);
    }

}
