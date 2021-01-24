package com.leilei.entity.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:37
 */
@ApiModel("用户添加表单")
@Data
public class UserAddForm {
    @ApiModelProperty(name = "用户名",required = true)
    private String name;
    private String nickName;
    @ApiModelProperty(name = "用户密码",required = true)
    private String password;
    @ApiModelProperty(name = "用户邮箱")
    private String email;
}
