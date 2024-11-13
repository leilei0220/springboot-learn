package com.leilei.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author: lei
 * @date: 2024-11-13 15:26
 * @desc:
 */
@TableName("student")
@KeySequence(value = "USER_SEQ")
public class Student {


    @TableId(value = "ID",type = IdType.INPUT)
    private Integer id;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
