package com.leilei.strategy;

import lombok.Data;

/**
 * @author lei
 * @create 2022-08-29 14:42
 * @desc 策略处理选择器
 **/
@Data
public class StrategyHandleSelector {

    private String handlerName;

    private Object data;

    private Long operatorTime;

    private Integer operatorType;
}
