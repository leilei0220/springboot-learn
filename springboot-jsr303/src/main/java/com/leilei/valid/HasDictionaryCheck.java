package com.leilei.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lei
 * @version 1.0
 * @date 2020/10/24 16:26
 * @desc HasDictionary 注解校验类...  可在此类中注入spring 管理的Bean
 */
public class HasDictionaryCheck implements ConstraintValidator<HasDictionary,Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //TODO 业务逻辑
        return value.equals("leilei");
    }
}
