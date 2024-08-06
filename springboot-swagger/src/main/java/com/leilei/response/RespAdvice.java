package com.leilei.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: lei
 * @date: 2024-08-06 15:35
 * @desc:
 */
@RestControllerAdvice
public class RespAdvice<T> implements ResponseBodyAdvice<T> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getDeclaringClass().isAnnotationPresent(RestController.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResultVO) {
            return o;
        }
        ResultVO<Object> objectResultVO = new ResultVO<>();
        objectResultVO.setData(o);
        return objectResultVO;
    }
}
