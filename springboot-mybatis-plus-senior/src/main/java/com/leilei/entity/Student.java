package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/6 21:56
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@TableName("student")
@Data
public class Student extends Model<Student> {
    private Long id;
    private String name;
    private Integer age;
    @Version
    private Integer version;

    @TableLogic
    private Boolean del_tag;


    @TableField(fill = FieldFill.INSERT)
    private Long createId;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Long modifyId;
    @TableField(fill = FieldFill.UPDATE)
    private Long modifyTime;
}
