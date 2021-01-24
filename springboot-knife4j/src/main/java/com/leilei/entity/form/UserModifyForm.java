package com.leilei.entity.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:37
 */
@ApiModel("用户修改表单")
@Data
public class UserModifyForm {
    @ApiModelProperty(name = "用户Id",required = true)
    private Long id;
    @ApiModelProperty(name = "用户名",required = true)
    private String name;
    @ApiModelProperty(name = "用户昵称")
    private String nickName;
    @ApiModelProperty(name = "用户邮箱")
    private String email;
    @ApiModelProperty(name = "版本号",required = true)
    private Integer version;
}
