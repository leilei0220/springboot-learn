package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 16:09
 * @desc
 */
@Data
@TableName("user")
public class User {
    private Integer id;
    private String username;
    private String password;
}
