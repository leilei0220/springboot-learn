package com.leilei.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/5 20:13
 * @Desc es 配置类
 */
@Configuration
public class ElasticSearchConfig {
    /**
     * 配置es 高级版 连接客户端
     * @return
     */
    @Bean
    public RestHighLevelClient highLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                //默认为Http连接
                RestClient.builder(
                        new HttpHost("localhost", 9200)));
        return client;
    }
}
