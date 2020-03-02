package com.leilei.controller;

import com.leilei.entity.one.RealEseate;
import com.leilei.entity.vo.BodyVo;
import com.leilei.service.AtomikosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : leilei
 * @date : 17:33 2020/3/1
 * @desc : 测试多数据眼事务
 */
@RestController
@RequestMapping("ato")
public class AtomikosController {
    @Autowired
    private AtomikosService atomikosService;

    /**
     * 测试多数据源事务  同时向两个事务插入数据 并制造异常测试是否回滚
     *
     * @param bodyVo
     * @return
     */
    @PostMapping("atoinsert")
    public Map<String, Object> atoinsert(@RequestBody BodyVo bodyVo) {
        return atomikosService.insertToTwoDatebaseService(bodyVo);
    }

    /**
     * 跨数据源连表查询
     * @param page
     * @param size
     * @return
     */
    @PostMapping("findall")
    public List<RealEseate> findall(Integer page, Integer size) {
        return atomikosService.findall(page, size);
    }
}
