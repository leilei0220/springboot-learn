package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 14:57
 * @desc
 */
@TableName("user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

    private Integer age;

    private String className;

    private String schoolName;

    private String createId;
}
