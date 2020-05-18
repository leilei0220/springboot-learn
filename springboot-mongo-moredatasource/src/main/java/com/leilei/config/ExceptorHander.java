package com.leilei.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lei
 * @date 2020/5/18 14:27
 * @desc
 */
@RestControllerAdvice
@Slf4j
public class ExceptorHander {

  /** 除数不能为0 */
  @ExceptionHandler({ArithmeticException.class})
  public AjaxResult arithmeticException(ArithmeticException ex) {
    log.error("除数不能为0：{} ", ex.getMessage(), ex);
    return AjaxResult.buildError("除数不能为0：/ by zero ");
  }
  /** 除数不能为0 */
  @ExceptionHandler({LeileiException.class})
  public AjaxResult LeileiException(LeileiException e) {
    log.error("leilei自定义异常：{} ", e.getMsg());
    return AjaxResult.buildError("leilei自定义异常==>"+e.getMsg());
  }
}
