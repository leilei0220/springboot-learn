package com.leilei.support;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lei
 * @create 2022-03-23 14:39
 * @desc 请求合并工具类
 **/
@Log4j2
public class BatchExecutor<T, R> {
    private static final Map<Class<?>, BatchExecutor> BATCH_INSTANCE =new ConcurrentHashMap<>();
    private static final ScheduledExecutorService SCHEDULE_EXECUTOR = Executors.newScheduledThreadPool(1);

    private final LinkedBlockingDeque<T> batchContainer = new LinkedBlockingDeque<>();
    private final BatchHandler<List<T>, R> handler;
    private final int countThreshold;

    /**
     * constructor
     *
     * @param handler        处理器
     * @param countThreshold 数量阈值，达到此阈值后触发处理器
     * @param timeThreshold  时间阈值，达到此时间后触发处理器
     */
    private BatchExecutor(BatchHandler<List<T>, R> handler, int countThreshold, long timeThreshold) {
        this.handler = handler;
        this.countThreshold = countThreshold;
        SCHEDULE_EXECUTOR.scheduleAtFixedRate(() -> {
            try {
                this.pullAndHandler(BatchHandlerType.BATCH_HANDLER_TYPE_TIME);
            } catch (Exception e) {
                log.error("pull container exception", e);
            }
        }, timeThreshold, timeThreshold, TimeUnit.SECONDS);
    }

    /**
     * 添加请求元素入队
     * @param event
     */
    public void submitEvent(T event) {
        batchContainer.add(event);
        if (batchContainer.size() >= countThreshold) {
            pullAndHandler(BatchHandlerType.BATCH_HANDLER_TYPE_DATA);
        }
    }

    /**
     * 从队列获取请求,并进行批量处理
     * @param handlerType
     */
    private void pullAndHandler(BatchHandlerType handlerType) {
        List<T> tryHandlerList = Collections.synchronizedList(new ArrayList<>(countThreshold));
        batchContainer.drainTo(tryHandlerList, countThreshold);
        if (tryHandlerList.size() < 1) {
            return;
        }
        try {
            R handle = handler.batchHandle(tryHandlerList, handlerType);
            log.info("批处理工具执行result:{}", handle);
        } catch (Exception e) {
            log.error("batch execute error, transferList:{}", tryHandlerList, e);
        }
    }

   /**
    * 获取合并器实例
    *
    * @param batchHandler 处理执行器
    * @param countThreshold  阈值数量(队列数量)
    * @param timeThreshold 阈值时间 单位秒（目前设置是触发后获取阈值数量请求，可根据需要修改）
    * @return BatchExecutor<T,R>
    * @author lei
    * @date 2022-09-23 15:10:17
    */
    public static <T, R> BatchExecutor<T,R> getInstance(BatchHandler<List<T>, R> batchHandler, int countThreshold, long timeThreshold) {
        Class<?> jobClass = batchHandler.getClass();
        if (BATCH_INSTANCE.get(jobClass) == null) {
            synchronized (BatchExecutor.class) {
                BATCH_INSTANCE.putIfAbsent(jobClass, new BatchExecutor<>(batchHandler, countThreshold, timeThreshold));
            }
        }
        return BATCH_INSTANCE.get(jobClass);
    }

    /**
     * 请求处理接口
     *
     * @param <IN> 批输入
     * @param <OUT> 结果输出
     */
    public interface BatchHandler<IN, OUT> {
        /**
         * 处理用户具体请求
         *
         * @param input
         * @param handlerType
         * @return
         */
        OUT batchHandle(IN input, BatchHandlerType handlerType);
    }

    /**
     * 合并执行类型枚举
     */
    public enum BatchHandlerType {
        /**
         * 数量类型
         */
        BATCH_HANDLER_TYPE_DATA,

        /**
         * 时间类型
         */
        BATCH_HANDLER_TYPE_TIME,
    }
}