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
 */
public class TimeSubTablePreciseAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        return shardingValue.getLogicTableName() + "_" + toDate(shardingValue.getValue());
    }

    public static String toDate(Long dateTimeMillSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date(dateTimeMillSec));
    }
}