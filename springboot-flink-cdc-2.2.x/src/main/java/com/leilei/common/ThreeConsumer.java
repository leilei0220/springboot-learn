package com.leilei.common;

/**
 * @author lei
 * @create 2022-09-20 14:22
 * @desc 三参数消费者
 **/
@FunctionalInterface
public interface ThreeConsumer<K, T, V> {

    /**
     * 三参数消费者
     *
     * @param k
     * @param t
     * @param v
     * @return void
     * @author lei
     * @date 2022-09-20 14:23:42
     */
    void accept(K k, T t, V v);
}
