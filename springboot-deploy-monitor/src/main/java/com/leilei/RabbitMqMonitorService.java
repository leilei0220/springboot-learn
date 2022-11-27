package com.leilei;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leilei.entity.QueueInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 * @create 2022-11-27 13:03
 * @desc rabbitmq监控
 **/
@Service
public class RabbitMqMonitorService {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    public static final String QUEUE_URL = "http://%s:15672/api/queues?page=1&page_size=100&name=&use_regex=false&sort=messages&sort_reverse=true&pagination=true";

    /**
     * 队列监控
     *
     * @param
     * @return void
     * @author lei
     * @date 2022-11-27 18:05:57
     */
    @Scheduled(fixedDelay = 10000)
    public void queueMonitor() {
        HttpRequest request = HttpUtil.createGet(String.format(QUEUE_URL, rabbitHost));
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Authorization", "Basic " + Base64.encode(rabbitUsername + ":" + rabbitPassword));
        request.addHeaders(headers);
        HttpResponse execute = request.execute();
        if (execute.isOk()) {
            String body = execute.body();
            System.out.println(body);
            JSONObject jsonObject = JSON.parseObject(body);
            String items = jsonObject.getString("items");
            List<QueueInfo> queueInfos = JSON.parseArray(items, QueueInfo.class);
            System.out.println(JSON.toJSONString(queueInfos));
        }
    }


}
