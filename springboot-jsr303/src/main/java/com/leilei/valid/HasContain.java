package com.leilei.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2020/10/24 17:04
 * @desc 自定义值 参数校验  参数必须是（1,2,3）中所包含的数
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Constraint(validatedBy = HasContainCheck.class)
public @interface HasContain {
    //自己设置的校验值
    String values();
    //默认提示信息
    String message() default " ";
    //默认分组
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
