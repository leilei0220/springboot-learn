package com.leilei;

import com.leilei.entity.User;
import com.leilei.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class SpringbootMybatisApplicationTests {
    @Autowired
    private IUserService userService;

    /**
     * 测试批量新增
     */
    @Test
    void contextLoads() {
        System.out.println(userService.insertMore(Arrays.asList(new User("张三", "111"), new User("李四", "222"))));
    }

    /**
     * 测试批量删除
     */
    @Test
    public void test() {
        System.out.println(userService.rmMore(Arrays.asList(5L, 6L,7L,8L,9L,10L)));
    }

}
