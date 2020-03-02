package com.leilei.controller;

import com.leilei.entity.one.RealEseate;
import com.leilei.service.IRealEseateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : leilei
 * @date : 17:10 2020/3/1
 * @desc : 第二个数据源 测试
 */
@RestController
@RequestMapping("real")
public class RealController {
    @Autowired
    private IRealEseateService realEseateService;

    @RequestMapping("findall")
    public List<RealEseate> findall() {
        return realEseateService.selectAll();
    }

    @PostMapping("insert")
    public Long insert(RealEseate realEseate) {
        realEseateService.insert(realEseate);
        return realEseate.getId();
    }
}
