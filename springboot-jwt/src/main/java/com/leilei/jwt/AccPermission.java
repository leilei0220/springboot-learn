package com.leilei.jwt;

import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 18:20
 * @desc
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccPermission {
    /**
     * jwt校验，如果设置为false则表示接口不需要校验token合法性
     * @return
     */
    boolean jwt() default true;
}
