package com.leilei;

import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import com.leilei.util.ListSplitUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest(classes = SpringbootMybatisPlusBatchApplication.class)
@RunWith(SpringRunner.class)
public class SpringbootMybatisPlusBatchApplicationTests {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void contextLoads() {
        userMapper.selectList(null);
    }

    @Test
    public void testInsetBatch() {
        ArrayList<User> users = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("AA" + UUID.randomUUID().toString());
            user.setAge(random.nextInt(10) + 10);
            users.add(user);
        }
        final int i = userMapper.insertBatch(users);
        System.out.println(i);
        System.out.println(i);
        System.out.println(i);
    }

    @Test
    public void testUpdateBatch() {
        final List<User> updateList = userMapper.selectList(null).stream().peek(e -> e.setAge(e.getAge() + 100)).collect(Collectors.toList());
        final List<List<User>> lists = ListSplitUtil.splitList(updateList, 1000);
        lists.forEach(e -> userMapper.updateBatch(e));
    }

}
