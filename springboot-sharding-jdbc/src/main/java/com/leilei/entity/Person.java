package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lei
 * @create 2024-07-03 16:54
 * @desc
 **/
@TableName("person")
@Data
public class Person {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
}
