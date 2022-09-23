package com.leilei.support;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lei
 * @create 2022-03-23 14:39
 * @desc 异步操作工具类
 **/
public class CompletableFutureUtils<R, E> {
    private final static int LIMIT = 500;

    private final Executor executor =  new ThreadPoolExecutor(
            5, 20,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("async-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 异步批量操作
     * @param list
     * @param fun
     * @return
     */
    public List<R> solveBatch(List<E> list, Function<List<E>, List<R>> fun){
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<List<E>> lists = Lists.partition(list, LIMIT);
        List<CompletableFuture<List<R>>> collect = lists.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> fun.apply(e), executor))
                .collect(Collectors.toList());
        List<List<R>> completableFutureList = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        List<R> result = completableFutureList.stream().filter(CollectionUtil::isNotEmpty).flatMap(Collection::stream).collect(Collectors.toList());
        return result;
    }


    /**
     * 异步单个惭怍
     * @param list
     * @param fun
     * @return
     */
    public List<R> solve(List<E> list, Function<E, R> fun){
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        List<CompletableFuture<R>> collect = list.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> fun.apply(e), executor))
                .collect(Collectors.toList());
        List<R> result = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return result;
    }


}