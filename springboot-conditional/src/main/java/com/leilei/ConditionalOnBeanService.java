package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-13 14:27
 * @desc  ConditionalOnBean  有指定的Bean类 才会加载bean
 **/
@ConditionalOnBean(ConditionalOnPropertyService.class)
@Service
public class ConditionalOnBeanService {
}
