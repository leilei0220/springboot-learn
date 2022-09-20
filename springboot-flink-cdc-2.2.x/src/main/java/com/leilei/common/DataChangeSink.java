package com.leilei.common;

import com.leilei.mysql.OperatorTypeEnum;
import com.leilei.strategy.selecter.StrategyEnum;
import com.leilei.strategy.selecter.StrategyHandleSelector;
import com.leilei.strategy.handler.BaseLogHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lei
 * @create 2022-08-25 14:01
 * @desc
 **/
@Component
@Log4j2
public class DataChangeSink implements SinkFunction<DataChangeInfo>, Serializable {

    Map<String, BaseLogHandler> strategyHandlerMap;

    public DataChangeSink(Map<String, BaseLogHandler> strategyHandlerMap) {
        this.strategyHandlerMap = strategyHandlerMap;
    }

    @Override
    public void invoke(DataChangeInfo value, Context context) {
        log.info("收到变更原始数据:{}", value);
        // todo 数据处理;因为此sink也是交由了spring管理，您想进行任何操作都非常简单
        StrategyHandleSelector selector = StrategyEnum.getSelector(value);
        if (selector == null) {
            return;
        }
        BaseLogHandler<Object> handler = strategyHandlerMap.get(selector.getHandlerName());
        if (selector.getOperatorType().equals(OperatorTypeEnum.INSERT.getType())) {
            handler.handleInsertLog(selector.getData(), selector.getOperatorTime());
            return;
        }
        if (selector.getOperatorType().equals(OperatorTypeEnum.UPDATE.getType())) {
            handler.handleUpdateLog(selector.getData(), selector.getOperatorTime());
            return;
        }
        if (selector.getOperatorType().equals(OperatorTypeEnum.DELETE.getType())) {
            handler.handleDeleteLog(selector.getData(), selector.getOperatorTime());
        }

    }
}
