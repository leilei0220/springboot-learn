package com.leilei.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Objects;

/**
 * @author lei
 * @version 1.0
 * @date 2021/3/2 21:52
 * @desc 自定义精准分片策略 根据Zone（数据库）
 */
public class SubDataBasePreciseAlgorithm implements PreciseShardingAlgorithm<String> {
    /**
     * @param availableTargetNames 所有的分片集 由于我这个算法是指定了分表算法，则这里是库列表即names 指定的名字列表
     * @param shardingValue 分片键（指定的那列作为分片条件）
     * @return
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        System.out.println("真实库列表:" + availableTargetNames);
        for (String databaseName : availableTargetNames) {
            String zoneValue = shardingValue.getValue();
            zoneValue = zoneValue == null || Objects.equals(zoneValue.replace(" ", ""), "") ? "sc" : zoneValue;
            if (databaseName.endsWith(zoneValue)) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException();
    }
}
