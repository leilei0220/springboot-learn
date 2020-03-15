package com.leilei.controller;

import com.leilei.service.ILinkListService;
import com.leilei.util.response.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : leilei
 * @date : 11:28 2020/3/15
 * @desc : mongodb 连表查询外暴接口
 */
@RestController
@RequestMapping("mongo")
public class LinkListController {
    @Autowired
    private ILinkListService linkListService;

    /**
     * 添加测试数据
     *
     * @return
     */
    @GetMapping("addData")
    public JsonReturn addData() {
        return linkListService.addData();
    }

    /**
     * 两张表 多对一测试
     */
    @GetMapping("moreToOne")
    public JsonReturn MoreToOne(Long studentId, Long classId) {
        return linkListService.MoreToOne(studentId, classId);
    }

    /**
     * 多张表一对一  一个学生 对应一个教室 一个教室对应一个学校
     */
    @GetMapping("moreTableOneToOne")
    public JsonReturn moreTableOneToOne() {
        return linkListService.moreTableOneToOne();
    }

    /**
     * 一对多测试
     */
    @GetMapping("oneToMany")
    public JsonReturn oneToMany() {

        return linkListService.oneToMany();
    }
}
