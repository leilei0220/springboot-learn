package com.leilei.support;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
public final class CompletableFutureUtils {

    private CompletableFutureUtils() {
    }

    private static final int LIMIT = 500;

    private static final Executor EXECUTOR =  new ThreadPoolExecutor(
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
    public static <E,R> List<R> mapBatch(List<E> list, Function<List<E>, List<R>> fun){
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List<List<E>> lists = Lists.partition(list, LIMIT);
        List<CompletableFuture<List<R>>> collect = lists.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> fun.apply(e), EXECUTOR))
                .collect(Collectors.toList());
        List<List<R>> completableFutureList = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return completableFutureList.stream().filter(CollectionUtil::isNotEmpty).flatMap(Collection::stream).collect(Collectors.toList());
    }


    /**
     * 异步单个惭怍
     * @param list
     * @param fun
     * @return
     */
    public static <E,R> List<R> map(List<E> list, Function<E, R> fun){
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List<CompletableFuture<R>> completableFutureList = list.stream()
                .map(e -> CompletableFuture.supplyAsync(() -> fun.apply(e), EXECUTOR))
                .collect(Collectors.toList());
        return completableFutureList.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        List<String> result1 = map(strings, String::toUpperCase);
        List<String> result2 = mapBatch(strings, a -> a.stream().map(String::toUpperCase).collect(Collectors.toList()));
        System.out.println(result1);
        System.out.println(result2);
    }


}