package com.leilei.version;

import com.leilei.SpringbootApiVersionApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author lei
 * @create 2022-11-02 22:27
 * @desc
 **/
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    public static final String[] EMPTY_PATTERN = new String[0];
    @Value("${scanner.package:com.leilei}")
    private String scanPackage;

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    public void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        Class<?> controllerClass = method.getDeclaringClass();
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(controllerClass, ApiVersion.class);
        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if (methodAnnotation != null) {
            apiVersion = methodAnnotation;
        }
        if (controllerClass.getName().startsWith(scanPackage) && apiVersion == null) {
            throw new RuntimeException(controllerClass.getName() + "." + method.getName() + "未配置VPI版本");
        }
        String[] urlPatterns = apiVersion == null ? EMPTY_PATTERN : apiVersion.version();
        PatternsRequestCondition apiPattern = new PatternsRequestCondition(urlPatterns);
        PatternsRequestCondition oldPattern = mapping.getPatternsCondition();
        PatternsRequestCondition updatedFinalPattern = apiPattern.combine(oldPattern);
        mapping = new RequestMappingInfo(mapping.getName(), updatedFinalPattern, mapping.getMethodsCondition(),
                mapping.getParamsCondition(), mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                mapping.getProducesCondition(), mapping.getCustomCondition());
        super.registerHandlerMethod(handler, method, mapping);
    }
}
