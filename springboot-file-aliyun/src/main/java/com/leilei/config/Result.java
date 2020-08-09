package com.leilei.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;

    private String message;

    private T data;

    /**
     * 请求成功
     *
     * @return
     */
    public static  Result ok() {
        return  Result.builder()
                .code(200)
                .message("success")
                .build();
    }

    public static <T>  Result<T> ok(T data) {
        return  Result.<T>builder()
                .code(200)
                .message("success")
                .data(data)
                .build();
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static  Result fail() {
        return  Result.builder()
                .code(-1)
                .message("fail")
                .build();
    }

    public static <T>  Result<T> fail(String message) {
        return  Result.<T>builder()
                .code(-1)
                .message(message)
                .build();
    }
}