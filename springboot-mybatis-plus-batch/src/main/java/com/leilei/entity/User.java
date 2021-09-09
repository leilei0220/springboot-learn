package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author lei
 * @create 2021-09-09 11:05
 * @desc 用户
 **/
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
}
