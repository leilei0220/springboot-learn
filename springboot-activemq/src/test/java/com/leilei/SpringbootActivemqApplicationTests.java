package com.leilei;

import com.leilei.easy.EasyProvider;
import com.leilei.topic.ActiveTopicProvider;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootActivemqApplicationTests {
    @Autowired
    private EasyProvider easyProvider;
    @Autowired
    private ActiveTopicProvider activeTopicProvider;
    @Test
    void contextLoads() {
        easyProvider.send();
    }
    @Test
    void test() {
        activeTopicProvider.sendMessage();
    }


}
