package com.leilei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.sms")
@Data
/**
 * Author: lei-p
 * Date: 2020/4/6 17:42
 * desc: 自定义bean 读取短信配置信息
 */
public class SmsBean {
    /**
     * 阿里云AK
     */
    private String accessKeyId;
    /**
     * 阿里云SK
     */
    private String accessKeySecret;
    /**
     * 阿里云短信末班ID
     */
    private String templateCode;
    /**
     * 阿里云短信签名
     */
    private String signName;
}
