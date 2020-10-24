package com.leilei.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(value = {MethodArgumentNotValidException.class ,BindException.class})
  public AjaxResult runtimeExceptionHandler(Exception ex) {
    log.error("参数异常：{}", ex.getMessage());
    BindingResult bindingResult = null;
    if (ex instanceof MethodArgumentNotValidException) {
      bindingResult = ((MethodArgumentNotValidException)ex).getBindingResult();
    } else if (ex instanceof BindException) {
      bindingResult = ((BindException)ex).getBindingResult();
    }
    StringBuilder errorMessage = new StringBuilder();
    if (bindingResult != null) {
      bindingResult.getFieldErrors().parallelStream().forEach(e -> errorMessage.append(e.getDefaultMessage()).append(" !"));
    }

    return AjaxResult.error(errorMessage.toString(), 400);
  }

  /** http 不可读 */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public AjaxResult httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
    log.error("运行时异常：{}", ex.getMessage(), ex);
    return AjaxResult.error("http 不可读，可能是参数出问题了",-1);
  }
  /** 运行时异常 */
  @ExceptionHandler(RuntimeException.class)
  public AjaxResult runtimeExceptionHandler(RuntimeException ex) {
    log.error("运行时异常：{}", ex.getMessage(), ex);
    return AjaxResult.error("服务器异常",-1);
  }


}

