package com.leilei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/9 11:46
 * @desc
 */
@Component
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliYunOssConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String fileHost;
    private String prefixUrl=bucketName+"."+endpoint;
}
