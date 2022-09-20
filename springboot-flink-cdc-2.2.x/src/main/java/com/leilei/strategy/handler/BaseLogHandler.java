package com.leilei.strategy.handler;

import java.io.Serializable;

/**
 * @author lei
 * @create 2022-08-29 14:22
 * @desc 日志处理器
 **/
public interface BaseLogHandler<T> extends Serializable {

    /**
     * 日志处理
     *
     * @param data         数据转换后模型
     * @param operatorTime 操作时间
     * @return void
     * @author lei
     * @date 2022-08-29 14:24:07
     */
    void handleInsertLog(T data, Long operatorTime);

    /**
     * 日志处理
     *
     * @param data         数据转换后模型
     * @param operatorTime 操作时间
     * @return void
     * @author lei
     * @date 2022-08-29 14:24:07
     */
    void handleUpdateLog(T data, Long operatorTime);

    /**
     * 日志处理
     *
     * @param data         数据转换后模型
     * @param operatorTime 操作时间
     * @return void
     * @author lei
     * @date 2022-08-29 14:24:07
     */
    void handleDeleteLog(T data, Long operatorTime);

}
