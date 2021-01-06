package com.leilei.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lei
 * @date 2020/5/19 16:04
 * @desc 自定义实体 读取 配置文件中的信息
 */
@Data
@ConfigurationProperties(prefix = "leilei")
@Component
public class Author {
    /**
     * 实体中字段名需与配置中相对应
     */
    private String name;
    private Integer age;
    private Boolean sex;
    private List<Girl> girlfriends = new ArrayList<>();
    private List<String> hobbys = new ArrayList<>();
    private ConcurrentHashMap<String, Object> family;
    private HashMap<Integer, User> zs;
    @Data
    public static class User{
        private Integer aa;
        private Integer bb;
    }
}
