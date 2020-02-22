package com.leilei.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : leilei
 * @date : 14:36 2020/2/22
 * @desc :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartMent {
    @Excel(name = "部门")
    private String name;
}
