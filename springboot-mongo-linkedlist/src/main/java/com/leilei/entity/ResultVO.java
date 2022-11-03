package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @create 2022-10-21 14:50
 * @desc 统一响应模型
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    public static Integer SUCCESS = 0;
    public static Integer FAIL = -1;

    private Integer code;
    private String message;
    private T data;

    public static <T> ResultVO<T> ok(T data) {
        return ResultVO.<T>builder().code(SUCCESS).message("操作成功").data(data).build();
    }

    public static <T> ResultVO<T> fail(String message) {
        return ResultVO.<T>builder().code(FAIL).message(message).data(null).build();
    }

    public static <T> ResultVO<T> fail() {
        return ResultVO.<T>builder().code(FAIL).message("操作失败").data(null).build();
    }
}
