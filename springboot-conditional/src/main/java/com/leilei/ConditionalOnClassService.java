package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-13 14:31
 * @desc  ConditionalOnClass 有指定的类时才加载该bean
 **/
@ConditionalOnClass(value = {com.leilei.MyConfig.class})
@Service
public class ConditionalOnClassService {
}
