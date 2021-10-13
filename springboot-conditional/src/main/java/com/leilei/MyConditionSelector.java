package com.leilei;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.beans.Introspector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lei
 * @create 2021-10-13 14:33
 * @desc 自定义条件加载bean
 **/
public class MyConditionSelector implements Condition {

    /**
     *
     * @param context
     * @param metadata
     * @return
     * Match 实现了Condition，重写 matches方法，当该方法返回true的时候，表示条件满足。
     *
     * 从参数context里面可以获取
     *
     * 1.context.getEnvironment() 环境信息   例如
     *
     * String osName = context.getEnvironment().getProperty("os.name");  获取操作系统名称
     * 2.context.getRegistry()  注册的bean
     *
     * 3.context.getResourceLoader() 加载的资源
     *
     * 4.context.getBeanFactory()   beanFactory
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 为true 使用该规则的bean 装配条件则会生效，对应bean会实例化
        final BeanDefinitionRegistry registry = context.getRegistry();
        final String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        // 我这里这是判断 是否存在 ConditionalOnPropertyService类的bean对象，存在则装配条件有效
        return Stream.of(beanDefinitionNames).collect(Collectors.toSet())
                .contains(Introspector.decapitalize(ConditionalOnPropertyService.class.getSimpleName()));
    }
}
