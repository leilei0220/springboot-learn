package com.leilei.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author lei
 * @create 2022-11-27 17:10
 * @desc 队列信息
 **/
@Data
public class QueueInfo {

    /**
     * 队列名
     */
    private String name;

    /**
     * 消费者连接数
     */
    private Integer consumers;

    /**
     * 是否持久化
     */
    private Boolean durable;

    /**
     * 队列状态
     */
    public String state;

    /**
     * 队列消息数量
     */
    public Integer messages;

    /**
     * 队列大小 单位byte
     */
    @JSONField(name = "message_bytes")
    public Long queueSize;

}
