package com.leilei.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 22:11 2020/3/8
 * @desc :
 */
@Data
public class User implements Serializable {
    @ApiModelProperty(name = "username",value = "用户名",example = "张三/李四",required = true)
    private String username;
    @ApiModelProperty(name = "nick",value = "昵称",example = "小狗狗")
    private String nick;
    @ApiModelProperty(name = "sex",value = "性别")
    private Boolean sex;
}
