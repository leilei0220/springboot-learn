package com.leilei.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lei
 * @version 1.0
 * @date 2020-10-19 12:41
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjaxResult {
    private Integer code;
    private String message;
    private Object data;


    public static AjaxResult success(Object data) {
       return AjaxResult.builder().code(200).message("success").data(data).build();
    }
    public static AjaxResult error(String message,Integer code) {
       return AjaxResult.builder().code(code).message(message).build();
    }

}
