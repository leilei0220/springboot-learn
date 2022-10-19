package com.leilei.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 16:21
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjaxResult<T> {
    private Integer code;
    private String message;
    private T data;


    public static <T> AjaxResult<T> success(T data) {
        return AjaxResult.<T>builder().code(200).message("success").data(data).build();
    }

    public static <T> AjaxResult<T> error(String message, Integer code) {
        return AjaxResult.<T>builder().code(code).message(message).build();
    }

    public static <T> AjaxResult<T> error(String message) {
        return AjaxResult.<T>builder().code(-1).message(message).build();
    }

}
