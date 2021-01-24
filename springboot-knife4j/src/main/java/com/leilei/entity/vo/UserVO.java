package com.leilei.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:56
 */
@Data
@ApiModel("用户响应对象")
public class UserVO {
    @ApiModelProperty(name = "用户Id")
    private Long id;
    @ApiModelProperty(name = "用户名")
    private String name;
    @ApiModelProperty(name = "用户昵称")
    private String nickName;
    @ApiModelProperty(name = "用户邮箱")
    private String email;
    @ApiModelProperty(name = "版本号")
    private Integer version;
}
