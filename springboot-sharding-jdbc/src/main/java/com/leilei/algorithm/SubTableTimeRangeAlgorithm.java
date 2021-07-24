package com.leilei.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lei
 * @version 1.0
 * @date 2021/2/28 20:59
 * @desc 自定义范围分表算法 （根据时间范围筛选数据表）
 */
public class SubTableTimeRangeAlgorithm implements RangeShardingAlgorithm<Long> {
    private static final String ACTUAL_TABLE_PREFIX = "vehicle_alarm_";
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * @param availableTargetNames 所有的分片集 由于我这个算法是指定了分表算法，则这里是逻辑表名列表 目前则为"vehicle_alarm"
     * @param shardingValue        分片键（指定的那列作为分片条件）
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        System.out.println("范围表策略触发--逻辑表名:" + availableTargetNames);
        Range<Long> range = shardingValue.getValueRange();
        Set<String> collect = buildRangeTime(range.lowerEndpoint(), range.upperEndpoint())
                .stream()
                .map(date -> ACTUAL_TABLE_PREFIX + date)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
        return collect;
    }


    public static Set<Integer> buildRangeTime(Long startMill, Long endMill) {
        LocalDate startDate = Instant.ofEpochMilli(startMill).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(endMill).atZone(ZoneOffset.ofHours(8)).toLocalDate();
        Integer start = Integer.valueOf(df.format(startDate));
        Integer end = Integer.valueOf(df.format(endDate));
        if (end.equals(start)) {
            return Collections.singleton(end);
        }
        Set<Integer> result = new TreeSet<>();
        long between = ChronoUnit.MONTHS.between(startDate, endDate);
        result.add(start);
        result.add(end);
        for (int i = 1; i <= between; i++) {
            result.add(Integer.valueOf(df.format(endDate.minusMonths(i))));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(buildRangeTime(1616682435000L, System.currentTimeMillis()));
    }

}
