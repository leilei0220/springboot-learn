package com.leilei.controller;

import com.leilei.entity.ResultVO;
import com.leilei.service.LinkListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : leilei
 * @date : 11:28 2020/3/15
 * @desc : mongodb 连表查询外暴接口
 */
@RestController
@RequestMapping("mongo")
public class LinkListController {
    private final LinkListService linkListService;

    public LinkListController(LinkListService linkListService) {
        this.linkListService = linkListService;
    }

    /**
     * 添加测试数据
     *
     * @return
     */
    @GetMapping("addData")
    public ResultVO<HashMap<String, Object>> addData() {
        return linkListService.addData();
    }

    /**
     * 两张表 多对一测试
     */
    @GetMapping("moreToOne")
    public ResultVO<List<Map>> moreToOne(Long studentId, Long classId) {
        return linkListService.moreToOne(studentId, classId);
    }

    /**
     * 多张表一对一  一个学生 对应一个教室 一个教室对应一个学校
     */
    @GetMapping("moreTableOneToOne")
    public ResultVO<List<Map>> moreTableOneToOne() {
        return linkListService.moreTableOneToOne();
    }

    /**
     * 一对多测试
     */
    @GetMapping("oneToMany")
    public ResultVO<List<Map>> oneToMany() {

        return linkListService.oneToMany();
    }
}
