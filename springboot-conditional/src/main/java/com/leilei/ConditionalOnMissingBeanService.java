package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-13 14:27
 * @desc  ConditionalOnMissingBean  没有指定的Bean类 才会加载bean
 **/
@ConditionalOnMissingBean(ConditionalOnPropertyService.class)
@Service
public class ConditionalOnMissingBeanService {
}
