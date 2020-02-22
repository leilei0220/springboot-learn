package com.leilei.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author : leilei
 * @date : 11:29 2020/2/22
 * @desc : name为在excel 名字 width：表格宽度  orderNum：处于excel第几行 默认为0   replace:替换 例如我的  true 替换为男  suffix：后缀  男生 女生
 * 普通导入导出所需字段注解
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Excel(name = "用户名", width = 15)
    private String username;

    @Excel(name = "密码", width = 15, orderNum = "4")
    private String password;

    @Excel(name = "性别", replace = {"男_true", "女_false"}, suffix = "生", width = 15, orderNum = "1")
    private Boolean sex;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "头像", width = 15, height = 15, type = 2, orderNum = "2")
    private String headimg;

    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat ="yyyy-MM-dd HH:mm:ss",width = 25, orderNum = "3")
    private LocalDateTime createTime;
    /**连表查询时  需要深入导出关联对象属相*/
    @ExcelEntity
    private DepartMent departMent;
}
