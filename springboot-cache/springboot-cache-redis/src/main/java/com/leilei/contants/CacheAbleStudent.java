package com.leilei.contants;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2021/8/29 15:49
 * @desc
 */
@Cacheable(value = CacheKey.STUDENT_DETAIL_KEY,
        key = "#id",unless = "#result == null ")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheAbleStudent {
}
