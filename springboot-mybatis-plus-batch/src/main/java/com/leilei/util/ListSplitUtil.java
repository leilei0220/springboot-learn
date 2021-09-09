package com.leilei.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lei
 * @create 2021-09-09 11:23
 * @desc 集合切割工具
 **/
public class ListSplitUtil {

    /**
     * 将集合切割为指定大小的元素块
     * @param list
     * @param splitSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int splitSize) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        int finalSplitSize = splitSize == 0 ? 10000 : splitSize;
        //计算分割后集合长度
        int maxSize = (list.size() + splitSize - 1) / splitSize;
        //开始分割
        return Stream.iterate(0, n -> n + 1)
                .limit(maxSize)
                .parallel()
                .map(x -> list.parallelStream().skip(x * finalSplitSize).limit(finalSplitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }
}
