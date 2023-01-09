package com.leilei.config;

/**
 * @author lei
 * @create 2023-01-09 16:34
 * @desc
 **/
public class ThreadContext {

   private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();


    public static void add(String s) {
        threadLocal.set(s);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
