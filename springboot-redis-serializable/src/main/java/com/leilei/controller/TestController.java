package com.leilei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.leilei.entity.Student;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @title：
 * @author: lei
 * @date: 2021年12月28日 20:55
 * @description:
 */
@RestController
@RequestMapping("/redis")
public class TestController {

    private final RedisTemplate<String,Object> redisTemplate;

    public TestController(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping
    public Object testSerialization() {
        List<Student> students = new ArrayList<>();
        Student ys = new Student("亚索", 122);
        Student mw = new Student("蛮王", 234);
        students.add(ys);
        students.add(mw);
        redisTemplate.opsForValue().set("大山", new Student("大山", 12345));
        redisTemplate.executePipelined((RedisCallback<String>) conn -> {
            students.forEach(s -> conn.set(s.getName().getBytes(), JSON.toJSONString(s).getBytes()));
            return null;
        },redisTemplate.getValueSerializer());
        List<Object> cacheList = redisTemplate.opsForValue().multiGet(students.stream().map(Student::getName).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(cacheList)) {
            return null;
        }
        List<Student> studentList = cacheList.stream().filter(Objects::nonNull).map(JSON::toJSONString).map(x -> JSON.parseObject(x, Student.class)).collect(Collectors.toList());
        System.out.println(studentList);
        return mw;
    }

}
