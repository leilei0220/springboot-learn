package com.leilei.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author lei
 * @version 1.0
 * @date 2020/10/24 17:04
 * @desc HasContain 校验类 可在此类中注入spring 管理的Bean
 */
public class HasContainCheck implements ConstraintValidator<HasContain, Object> {

    private String values;

    /**
     * 此方法是在加载该校验注解最先执行的方法，可在内做逻辑操作处理......
     *
     * @param hasContain
     */
    @Override
    public void initialize(HasContain hasContain) {
        this.values = hasContain.values();
    }

    /**
     * 参数校验规则
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String[] parameters = values.split(",");
        long count = Arrays.stream(parameters)
                .filter(value::equals)
                .count();
        return count > 0;
    }
}
