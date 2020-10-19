package com.leilei;


import com.leilei.common.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/01/20 10:21
 * @desc: 增强类  可以自己在 @ExceptionHandler(xxx.class)  定义异常类型  这样就会以json形式展示错误信息
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

  /** 运行时异常 */
  @ExceptionHandler(RuntimeException.class)
  public AjaxResult runtimeExceptionHandler(RuntimeException ex) {
    log.error("运行时异常：{}", ex.getMessage(), ex);
    return AjaxResult.error("服务器异常",500);
  }
  @ExceptionHandler(BindException.class)
  public AjaxResult runtimeExceptionHandler(BindException ex) {
    log.error("参数异常：{}", ex.getMessage());
    BindingResult bindingResult = ex.getBindingResult();
    StringBuilder errorMessage = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorMessage.append(fieldError.getDefaultMessage()).append("! ");
    }
    return AjaxResult.error(errorMessage.toString(), 400);
  }


}

