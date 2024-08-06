package com.leilei.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lei
 * @date: 2024-08-06 15:24
 * @desc:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    @ApiModelProperty("返回码")
    private int code;

    @ApiModelProperty("返回描述")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public static<T> ResultVO<T> ok(T data){
        return ResultVO.<T>builder().code(0).data(data).message("success").build();
    }


}
