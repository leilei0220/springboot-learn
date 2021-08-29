package com.leilei.contants;

import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2021/8/29 15:49
 * @desc
 */
@Cacheable(value = CacheKey.STUDENT_DETAIL_BY_CREATE_KEY,
        key = "#createId",unless="#result == null || #result.size() == 0")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheAbleStudentByCreateId {
}
