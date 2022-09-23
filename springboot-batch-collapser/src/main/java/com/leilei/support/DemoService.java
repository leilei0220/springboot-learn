package com.leilei.support;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author lei
 * @create 2022-09-23 16:04
 * @desc
 **/
@Service
public class DemoService  implements BatchExecutor.BatchHandler<List<String>, List<String>> {

    private BatchExecutor<String, List<String>> batchExecutor;
    @PostConstruct
    private void postConstructorInit() {
        // 当请求数量达到20个，或每过5s合并执行一次请求
        batchExecutor = BatchExecutor.getInstance(DemoService.this, 20, 5);
    }

    /**
     *
     *
     * @param input
     * @param handlerType
     * @return Integer
     * @author lei
     * @date 2022-09-23 16:05:49
     */
    @Override
    public List<String> batchHandle(List<String> input, BatchExecutor.BatchHandlerType handlerType) {
        System.out.println("处理类型:" + handlerType + ",接受到批量请求参数:" + input);
        return input.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    /**
     * 假设我这里是300ms一次请求
     */
    @Scheduled(fixedDelay = 300)
    public void aaa() {
        String s = UUID.randomUUID().toString();
        batchExecutor.submitEvent(s);
        System.out.println("当前请求参数:" + s);

    }
}
