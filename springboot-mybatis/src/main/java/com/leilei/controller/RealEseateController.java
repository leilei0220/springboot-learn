package com.leilei.controller;

import com.github.pagehelper.PageInfo;
import com.leilei.entity.RealEseate;
import com.leilei.service.IRealEseateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:05 2020/2/24
 * @desc :
 */
@RestController
@RequestMapping("real")
public class RealEseateController {
    @Autowired
    private IRealEseateService realEseateService;

    @GetMapping("findAll")
    public List<RealEseate> findAll() {
        return realEseateService.selectAll();
    }

    /**
     * 分页 排序查询
     *
     * @param page 当前页
     * @param size 每页最多展示多少条数据
     * @return
     */
    @PostMapping("findBypage")
    public PageInfo<RealEseate> findByPage(Integer page, Integer size) {
        return realEseateService.selectByPage(page, size);
    }

    /** 灵活多条件分页查询
     * @param page 当前页
     * @param size 每页最多展示多少条数据
     * @param projectName 项目名
     * @param address 地址
     * @return
     */
    @PostMapping("findQuery")
    public PageInfo<RealEseate> findQuery(Integer page, Integer size, String projectName, String address) {
        return realEseateService.selectByQuery(page, size, projectName, address);
    }
}
