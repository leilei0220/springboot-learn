package com.leilei;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lei
 * @date 2020/5/10 17:17
 * @desc 增强类  可以自己在 @ExceptionHandler(xxx.class)  定义异常类型  这样就会以json形式展示错误信息
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

  /**
   * 参数异常
   */
  @ExceptionHandler(BindException.class)
  public Map<String, Object> runtimeExceptionHandler(BindException ex) {
    Map<String, Object> map = new HashMap<>();
    log.error("参数异常：{}", ex.getMessage());
    map.put("head", false);
    map.put("status", 400);
    BindingResult bindingResult = ex.getBindingResult();
    String errorMesssage = "";
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorMesssage += fieldError.getDefaultMessage() + "! ";
    }
    map.put("content", errorMesssage);
    return map;
  }

  /**
   * 运行时异常
   */
  @ExceptionHandler(RuntimeException.class)
  public Map<String, Object> runtimeExceptionHandler(RuntimeException ex) {
    Map<String, Object> map = new HashMap<>();
    log.error("运行时异常：{}", ex.getMessage());
    map.put("head", false);
    map.put("status", 500);
    map.put("content", ex.getMessage());
    return map;
  }

}
