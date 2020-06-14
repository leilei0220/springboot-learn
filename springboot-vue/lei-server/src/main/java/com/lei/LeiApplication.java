package com.lei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author lei
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LeiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(LeiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  项目启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
