package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 11:22 2020/3/15
 * @desc :
 */
@Data
@Document(collation = "student")
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id 。不必细究(本文实体类中为id）
     */
    @Id
    private Long id;
    private String username;
    /**
     * 关联班级ID
     */
    private Long classId;
}
