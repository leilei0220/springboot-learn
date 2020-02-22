package com.leilei.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 15:43 2020/2/22
 * @desc : EasyPoi  校验导入数据合理性  校验所需注解  还有很多校验注解，本文没有一一罗列出来 具体可查看官网
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    @Excel(name = "用户名", width = 15)
    @NotNull(message = "用户名不能为空哦!!")
    private String username;

    @Excel(name = "密码", width = 15, orderNum = "4")
    @NotNull(message = "密码不能为空!!")
    private String password;

    @Excel(name = "性别", replace = {"男_true", "女_false"}, suffix = "生", width = 15, orderNum = "1")
    private Boolean sex;

    @Excel(name = "年龄")
    @Max(value = 65,message = "年龄太大了，不适合工作哦")
    private Integer age;

    @Excel(name = "头像", width = 15, height = 15, type = 2, orderNum = "2")
    private String headimg;

    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat ="yyyy-MM-dd HH:mm:ss",width = 25, orderNum = "3")
    private LocalDateTime createTime;
    /**连表查询时  需要深入导出关联对象属相*/
    @ExcelEntity
    private DepartMent departMent;
}
