package com.leilei.springbootredis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leilei.SpringbootRedisApplication;
import com.leilei.entity.User;
import com.leilei.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : leilei
 * @date : 17:51 2020/2/17
 * @desc :
 */
@SpringBootTest(classes = SpringbootRedisApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootredisTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testString() {
        redisUtil.set("a", 11111);
        System.out.println(redisUtil.get("a"));
        redisUtil.set("过期时间测试", 123, 50);
        redisUtil.del("a");
    }

    @Test
    public void testMap() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("aaa", 1);
        objectObjectHashMap.put("aaa", "aaa");
        objectObjectHashMap.put("a", 1);
        objectObjectHashMap.put("qweqwr", 1L);
        redisUtil.hmset("Map测试", objectObjectHashMap);

        Map<Object, Object> map = redisUtil.hmget("Map测试");
        for (Map.Entry<Object, Object> objectObjectEntry : map.entrySet()) {
            System.out.println(objectObjectEntry.getKey() + "-------" + objectObjectEntry.getValue());
        }

        Object hget = redisUtil.hget("Map测试", "a");
        System.out.println(hget);

        redisUtil.hdel("Map测试", "qweqwr");
    }

    @Test
    public void testMapSuper() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", 1L);
        map.put("b", 2D);
        map.put("c", 123);
        map.put("d", LocalDateTime.now());
        redisUtil.hmset("leilei:1:map", map);
        redisUtil.hmset("leilei:2:map", map);
        redisUtil.hmset("leilei:3:map", map);
        redisUtil.hmset("leilei:4:map", map);
    }
    @Test
    public void testMap2Bean (){
        Map<String, Object> map = new HashMap<>();
        map.put("username","王五");
        map.put("password","123");
        map.put("age",11);
        redisUtil.hmset("Map2Bean", map);
        Map<Object, Object> map2Bean = redisUtil.hmget("Map2Bean");
        User user = mapToBean(map2Bean, new User());
        System.out.println("Map2Bean"+user);
    }
    /**
     * map转对象
     *
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<Object, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    @Test
    public void testSet() {
        redisUtil.sSet("Set排序测试", 3, 1, 2, 5, 12, 666);
    }

    /**
     * fastjson 部分使用
     */
    @Test
    public void testFastJson (){

        //Java对象转json字符串
        User aa = new User("aa", "123456", 15);
        System.out.println(aa); //User(username=aa, password=123456, age=15)
        String s = JSON.toJSONString(aa);
        System.out.println(s);   //{"age":15,"password":"123456","username":"aa"}

        //json字符串转java对象
        String ss = "{\"age\":15,\"password\":\"123456\",\"username\":\"aa\"}";
        User user = JSON.parseObject(ss, User.class);
        System.out.println(user); //User(username=aa, password=123456, age=15)

        //Java集合转json字符串
        List<Serializable> serializables = Arrays.asList(new User("a", "a", 1), new User("b", "b", 2));
        String jsonString = JSON.toJSONString(serializables);
        System.out.println(jsonString); //[{"age":1,"password":"a","username":"a"},{"age":2,"password":"b","username":"b"}]

        //json字符串转java集合
        String jsonlistString = "[{\"age\":1,\"password\":\"a\",\"username\":\"a\"},{\"age\":2,\"password\":\"b\",\"username\":\"b\"}]";
        List<User> users = JSON.parseArray(jsonlistString, User.class);
        users.forEach(e-> System.out.println(e));  //User(username=a, password=a, age=1) User(username=b, password=b, age=2)

        //map转Json字符串
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("a", 1);
        objectObjectHashMap.put("b", 2L);
        objectObjectHashMap.put("c", 3D);
        objectObjectHashMap.put("d", 3f);
        objectObjectHashMap.put("f", LocalDateTime.now());
        String mapjsonString = JSON.toJSONString(objectObjectHashMap);
        System.out.println(mapjsonString);  //{"a":1,"b":2,"c":3.0,"d":3.0,"f":"2020-02-18T16:40:01.203"}

        //map转json对象
        JSONObject mapjsonobj = new JSONObject(objectObjectHashMap);
        System.out.println("map转json对象"+mapjsonobj);
        //json字符串转Map
        //需要将json字符串转为json对象   json对象再转为map
        JSONObject  jsonObject = JSONObject.parseObject(mapjsonString);
        Map<String,Object> map = (Map<String,Object>)jsonObject;
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + "-------" + stringObjectEntry.getValue());
            //a-------1
            //b-------2
            //c-------3.0
            //d-------3.0
            //f-------2020-02-18T16:45:37.452
        }


    }

    @Test
    public void testList() {
        redisUtil.lSet("LIst测试", 1);
        redisUtil.lSet("LIst测试", 2);
        redisUtil.lSet("LIst测试", "aaaa");
        List<Object> list = redisUtil.lGet("LIst测试", 1, 2);
        System.out.println(list);
        System.out.println(redisUtil.lGetListSize("LIst测试"));
        System.out.println(redisUtil.lGetIndex("LIst测试", 2));
        List<Object> listo = new ArrayList<>();
        listo.add(1);
        listo.add(2);
        User user = new User();
        user.setUsername("aa");
        user.setPassword("123");
        user.setAge(12);
        listo.add(JSON.toJSONString(user));
        redisUtil.lSet("list与json测试", listo);
        String s = (String) redisUtil.lGetIndex("list与json测试", 2);
        User user1 = JSON.parseObject(s, User.class);
        System.out.println(user1);
    }
}
