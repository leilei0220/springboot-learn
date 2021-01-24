package com.leilei.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultVO<T> {
    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回描述")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public static <T>  ResultVO<T> success(T data) {
        return ResultVO.<T>builder().code(0).message("请求成功").data(data).build();
    }
    public static <T> ResultVO<T> fail(String message,T data) {
        return ResultVO.<T>builder().code(-1).message(message).data(data).build();
    }
    public static ResultVO fail(Integer code,String message) {
        return ResultVO.builder().code(code).message(message).data(null).build();
    }
}
