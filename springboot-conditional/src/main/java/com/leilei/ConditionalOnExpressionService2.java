package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-18 11:23
 * @desc 根据条件判端是否加载bean
 *
 * conditional.property 为true 并且 root-age=1
 **/
@Service
@ConditionalOnExpression("${conditional.property:false} && ${root-age:0}==1")
public class ConditionalOnExpressionService2 {
}
