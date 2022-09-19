package com.leilei;

import com.leilei.demo.ReplyProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringbootRabbitmqReplyApplication.class)
public class SpringbootRabbitmqReplyApplicationTests {
    private final ReplyProvider provider;
    @Autowired
    public SpringbootRabbitmqReplyApplicationTests(ReplyProvider provider) {
        this.provider = provider;
    }

    @Test
    public void contextLoads() {
        provider.replySend();
    }

}
