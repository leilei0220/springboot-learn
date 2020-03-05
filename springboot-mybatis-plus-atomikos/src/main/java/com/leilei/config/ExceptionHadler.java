package com.leilei.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : leilei
 * @date : 14:20 2020/3/5
 * @desc : 自定义异常响应
 */
@RestControllerAdvice
public class ExceptionHadler {

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(HttpServletRequest req, Exception e) {
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("请求状态", "False");
        map.put("请求路径", req.getRequestURI());
        map.put("请求方式", req.getMethod());
        map.put("错误信息", e.getMessage());
        return map;
    }
}
