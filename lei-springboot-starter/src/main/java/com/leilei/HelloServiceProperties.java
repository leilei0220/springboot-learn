package com.leilei;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 读取配置信息
 */
@ConfigurationProperties(prefix = "com.leilei")
public class HelloServiceProperties {

    private String name = "默认值:panghu";

    private String hobby = "默认值:basketball";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
