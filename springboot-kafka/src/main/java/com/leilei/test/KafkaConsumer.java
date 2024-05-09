package com.leilei.test;

import com.alibaba.fastjson.JSON;
import com.leilei.entity.Location;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class KafkaConsumer {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ExecutorService executorService = new ThreadPoolExecutor(8, 32,
            30L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024), new ThreadFactory() {
        public final AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("consumer-" + atomicInteger.incrementAndGet());
            return thread;
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());
    @Value("${kafka.calcSleep:200}")
    private Integer sleep;
    /**
     * 单条消费  spring.kafka.listener.type=single
     *
     * @param consumerRecord
     */
    @KafkaListener(topics = "${kafka.calcTopic}", groupId = "${kafka.groupId}")
    public void consume1(ConsumerRecord<String, String> consumerRecord) {
        log.info("收到单条消息");
        log.info(consumerRecord);
        log.info(("Consumed message: " + consumerRecord.key() + "--" + consumerRecord.value()));
    }

    /**
     *
     * 批量消费  spring.kafka.listener.type=batch
     *
     * @param consumerRecords
     */
    @KafkaListener(topics = "${kafka.calcTopic}", groupId = "${kafka.groupId}")
    public void consume1(List<ConsumerRecord<String, String>> consumerRecords) {
        consumerRecords.forEach(consumerRecord -> executorService.execute(()->{
            Location location = JSON.parseObject(consumerRecord.value(), Location.class);
            log.info(("Batch-Consumed message: " + consumerRecord.key() + "--" + consumerRecord.value()) + "--" + location);
        }));
    }


}
