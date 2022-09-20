package com.leilei.strategy.selecter;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leilei.common.DataChangeInfo;
import com.leilei.strategy.entity.DriverPO;
import com.leilei.strategy.entity.GpsVehicleDriverBind;
import com.leilei.strategy.handler.DriverLogLogHandler;
import com.leilei.strategy.handler.SsVehicleDriverHandler;
import lombok.Getter;

import java.beans.Introspector;

/**
 * @author lei
 * @create 2022-08-29 14:14
 * @desc 表处理策略
 **/
public enum StrategyEnum {

    /**
     * 驾驶员处理器
     */
    DRIVER("base_business_driver_score", DriverPO.class, Introspector.decapitalize(DriverLogLogHandler.class.getSimpleName())),

    /**
     * sql-server 车辆驾驶员绑定处理器
     */
    SS_VEHICLE_DRIVER("GPSVehicleDriverBind", GpsVehicleDriverBind.class, Introspector.decapitalize(SsVehicleDriverHandler.class.getSimpleName())),
    ;

    @Getter
    private final String tableName;
    @Getter
    private final Class varClass;
    @Getter
    private final String handlerName;

    StrategyEnum(String tableName, Class varClass, String handlerName) {
        this.tableName = tableName;
        this.varClass = varClass;
        this.handlerName = handlerName;
    }

    /**
     * 策略选择器
     *
     * @param dataChangeInfo
     * @return StrategyHandlerSelector
     * @author lei
     * @date 2022-08-29 15:20:05
     */
    public static StrategyHandleSelector getSelector(DataChangeInfo dataChangeInfo) {
        if (dataChangeInfo == null) {
            return null;
        }
        String tableName = dataChangeInfo.getTableName();
        StrategyHandleSelector selector = new StrategyHandleSelector();
        for (StrategyEnum strategyEnum : values()) {
            if (strategyEnum.getTableName().equals(tableName)) {
                selector.setHandlerName(strategyEnum.handlerName);
                selector.setOperatorTime(dataChangeInfo.getOperatorTime());
                selector.setOperatorType(dataChangeInfo.getOperatorType());
                JSONObject jsonObject = JSON.parseObject(dataChangeInfo.getData());
                selector.setData(BeanUtil.copyProperties(jsonObject, strategyEnum.varClass));
                return selector;
            }
        }
        return null;
    }
}
