package com.leilei;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2021-10-18 11:23
 * @desc 根据条件判端是否加载bean
 **/
@Service
@ConditionalOnExpression("'${root-name:lei}'.equals('leilei') && ${conditional.property:false}")
public class ConditionalOnExpressionService {
}
