package com.leilei.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * @author lei
 * @version 1.0
 * @date 2021/2/28 20:59
 */
public class TimeSubTableRangeAlgorithm implements RangeShardingAlgorithm<Long> {
    private static final String ACTUAL_TABLE_PREFIX = "vehicle_alarm_";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();
        Range<Long> range = shardingValue.getValueRange();
        Integer start = toDateMonth(range.lowerEndpoint());
        int end = toDateMonth(range.upperEndpoint());
        for (int i = start; i <= end; i = toNextDateMonth(i)) {
            result.add(ACTUAL_TABLE_PREFIX + i);
        }
        return result;
    }

    public static Integer toDateMonth(Long dateTimeMillSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return Integer.valueOf(sdf.format(new Date(dateTimeMillSec)));
    }

    public static Integer toNextDateMonth(Integer dateMonth) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        try {
            Date date = dft.parse(dateMonth.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(date.getTime()));
            cal.add(Calendar.MONTH, 1);
            String preMonth = dft.format(cal.getTime());
            return Integer.valueOf(preMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
