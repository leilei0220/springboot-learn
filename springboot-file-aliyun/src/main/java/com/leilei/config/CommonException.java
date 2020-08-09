package com.leilei.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/9 12:43
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends Exception {
    private String message;
    private final int code = -1;

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException() {
    }
}
