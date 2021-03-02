package com.leilei.algorithm;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/3/2 22:19
 */
public class DbSelectUtil {
    public static ThreadLocal<List<String>> DB_SELECTOR = new ThreadLocal<>();
}
