package com.leilei.contants;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2021/8/29 15:50
 * @desc
 */
@CacheEvict(value = {
        CacheKey.STUDENT_DETAIL_KEY,
        CacheKey.STUDENT_DETAIL_ALL_KEY,
        CacheKey.STUDENT_DETAIL_BY_CREATE_KEY
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheEvictAllStudent {
}
