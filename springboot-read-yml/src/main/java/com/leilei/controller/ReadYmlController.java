package com.leilei.controller;

import com.leilei.entity.ApplicationInfo;
import com.leilei.entity.Author;
import com.leilei.entity.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @date 2020/5/19 16:13
 * @desc
 */
@RestController
@RequestMapping("getInfo")
public class ReadYmlController {


    @Value("${leilei.name}")
    private String name;

    @Value("#{'${leilei.hobbys}'.split(',')}")
    private List<String> hobbyList;


    @Autowired
    Environment environment;

    @Autowired
    private Author author;
    @Autowired
    private ApplicationInfo applicationInfo;

    @RequestMapping("redConfig")
    public Map<String, Object> getConfig() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ConfigurationProperties方式读取的自定义Author 信息", author);

        map.put("结合ConfigurationProperties 读取项目POM.xml信息", applicationInfo);

        map.put("@Value注解读取某配置信息", "作者姓名：" + name);
        map.put("@Value注解读取yml list信息", "作者的爱好们：" + hobbyList);
        map.put("Environment读取配置信息",
                "作者姓名：" + environment.getProperty("leilei.name") + " 作者年龄：" + environment
                        .getProperty("leilei.age"));

        map.put("读取自定义配置文件：",
                "数据库账户：" + MyProperties.getUsername() + " 数据库密码：" + MyProperties.getPassword());

        return map;
    }

    @RequestMapping("test")
    public String test() {
        return "读取自定义配置文件：" +
                "数据库账户：" + MyProperties.getUsername() + " 数据库密码：" + MyProperties.getPassword();
    }
}
