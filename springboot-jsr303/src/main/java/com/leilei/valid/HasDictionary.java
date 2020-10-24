package com.leilei.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lei
 * @version 1.0
 * @date 2020/10/24 16:24
 * @desc 自定义注解，用于校验是否在字典表中存在
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
//指定作用字段上以及方法参数上
@Target({ElementType.PARAMETER,ElementType.FIELD})
//指定校验规则类
@Constraint(validatedBy = HasDictionaryCheck.class)
public @interface HasDictionary {
    //默认提示信息
    String message() default "字典表中该值不存在";
    //默认分组
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
