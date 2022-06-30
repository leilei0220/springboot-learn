package com.leilei.support;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author lei
 * @create 2022-03-22 15:22
 * @desc
 **/
@Service
public class ProductService implements BatchCollapser.BatchHandler<List<Integer>, Integer> {
    private BatchCollapser<Integer, Integer> batchCollapser;

    @PostConstruct
    private void postConstructorInit() {
        // 当请求数量达到20个，或每过5s合并执行一次请求
        batchCollapser = BatchCollapser.getInstance(ProductService.this, 20, 5);
    }

    @Override
    public Integer handle(List<Integer> input, BatchCollapser.BatchHandlerType handlerType) {
        System.out.println("处理类型:" + handlerType + ",接受到批量请求参数:" + input);
        return input.stream().mapToInt(x -> x).sum();
    }


    /**
     * 假设我这里是300ms一次请求
     */
    @Scheduled(fixedDelay = 300)
    public void aaa() {
        Integer requestParam = (int) (Math.random() * 100) + 1;
        batchCollapser.addRequestParam(requestParam);
        System.out.println("当前请求参数:" + requestParam);

    }
}
