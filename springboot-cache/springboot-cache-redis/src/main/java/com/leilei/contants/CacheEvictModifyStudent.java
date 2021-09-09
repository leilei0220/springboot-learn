package com.leilei.contants;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2021/8/29 15:43
 * @desc
 */
@CacheEvict(value = CacheKey.STUDENT_DETAIL_KEY,
        key = "#student.id")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheEvictModifyStudent {
}
