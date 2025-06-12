package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author: lei
 * @date: 2025-06-12 15:19
 * @desc:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    private int code;

    private String message;

    private T data;

    public static <T> ResultVO<T> fail(String message) {
        return ResultVO.<T>builder()
                .code(-1)
                .message(message)
                .build();
    }

    /**
     * 请求成功
     *
     * @return
     */
    public static<T> ResultVO<T> ok() {
        return ResultVO.<T>builder()
                .code(0)
                .message("操作成功")
                .build();
    }

    public static <T> ResultVO<T> ok(T data) {
        return ResultVO.<T>builder()
                .code(0)
                .data(data)
                .message("操作成功")
                .build();
    }
}