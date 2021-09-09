package com.leilei.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei
 * @version 1.0
 * @date 2021/3/2 22:10
 * @desc 自定义范围分片策略 根据Zone（数据库）
 */
public class SubDataBaseRangeAlgorithm implements RangeShardingAlgorithm<String> {
    public static final String DB_PREFIX = "alarm-";

    /**
     * @param availableTargetNames 所有的分片集 由于我这个算法是指定了分表算法，则这里是库列表即names 指定的名字
     * @param shardingValue        分片键（指定的那列作为分片条件）
     * @return
     * @desc 由于我这里指定的一个业务列作为分库策略 比如zone为（四川 sc,北京 bj,山西 sx等）故此这里采用ThreadLocal 每次有涉及到分库操作时，
     * 先将需要查询的库，存入到threadLocal中。详情请查看单元测试
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        List<String> dbList = DbSelectUtil.DB_SELECTOR.get();
        System.out.println("选中db后缀:" + dbList);
        System.out.println("真实库列表:" + availableTargetNames);
        Collection<String> finaDbList = new LinkedList<>();
        if (dbList.isEmpty()) {
            finaDbList.add(DB_PREFIX + "sc");
        } else {
            finaDbList.addAll(dbList.parallelStream().map(e -> DB_PREFIX + e).collect(Collectors.toList()));
        }
        return finaDbList;
    }
}
