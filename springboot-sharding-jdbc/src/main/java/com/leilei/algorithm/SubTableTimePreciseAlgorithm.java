package com.leilei.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * @author lei
 * @version 1.0
 * @date 2021/2/28 16:59
 * @desc 自定义精准分表算法 （根据时间 按月分表）
 */
public class SubTableTimePreciseAlgorithm implements PreciseShardingAlgorithm<Long> {
    /**
     * @param availableTargetNames  所有的分片集 由于我这个算法是指定了分表算法，则这里是逻辑表名列表 目前则为"vehicle_alarm"
     * @param shardingValue 分片键（指定的那列作为分片条件）
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("精准表策略触发--逻辑表名:" + availableTargetNames);
        return shardingValue.getLogicTableName() + "_" + toDate(shardingValue.getValue());
    }

    public static String toDate(Long dateTimeMillSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date(dateTimeMillSec));
    }
}