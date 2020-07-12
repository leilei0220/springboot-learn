package com.leilei;

import com.leilei.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootElasticsearchTwoJpaApplicationTests {
    @Autowired
    ProductService productService;
    @Test
    void contextLoads() {
        productService.createOrUpdateIndex();
    }

}
