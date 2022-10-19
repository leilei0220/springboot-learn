package com.leilei.entity;

import lombok.Data;

/**
 * @author lei
 * @create 2022-08-26 14:51
 * @desc 分页查询表单
 **/
@Data
public class StudentQueryForm {
    private Integer pageIndex;
    private Integer pageSize;
    private String  username;
}
