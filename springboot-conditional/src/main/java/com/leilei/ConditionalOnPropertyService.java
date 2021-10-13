package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-13 14:06
 * @desc 指定的property属性有指定的值 才会加载该bean
 * ex:当存在配置属性  conditional.property 且值为true时才会加载该bean
 **/
@ConditionalOnProperty(value = "conditional.property",havingValue = "true")
@Service
public class ConditionalOnPropertyService {

}
