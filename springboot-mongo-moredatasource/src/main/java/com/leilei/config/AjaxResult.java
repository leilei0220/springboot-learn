package com.leilei.config;

import lombok.Data;

/**
 * @author lei
 * @date 2020/5/18 16:57
 * @desc
 */
@Data
public class AjaxResult {

  private Boolean head = true;
  private Integer code = 200;
  private String msg;
  private Object content;

  public AjaxResult() {
  }

  public AjaxResult(Boolean head, Integer code, String msg, Object content) {
    this.head = head;
    this.code = code;
    this.msg = msg;
    this.content = content;
  }

  public static AjaxResult buildSuccess(String msg, Object content) {
    return new AjaxResult(true, 200, msg, content);
  }

  public static AjaxResult buildError(String msg) {
    return new AjaxResult(false, 500, msg, null);
  }
}
