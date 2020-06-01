package com.leilei;

import com.leilei.service.IAuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {
    @Autowired
    private IAuthUserService authUserService;
    @Test
    void contextLoads() {
        System.out.println(authUserService.findOneByName("leilei"));
    }

}
