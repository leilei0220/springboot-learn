package com.leilei;

/**
 * 自定义一个服务
 */
public class HelloService {

    private String name;

    private String hobby;

    public String getName() {
        return "name is " + name;
    }

    public String getHobby() {
        return "hobby is " + hobby;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
