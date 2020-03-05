package com.leilei.controller;

import com.leilei.entity.vo.BodyVo;
import com.leilei.service.IAtoService;
import com.leilei.service.IRoleService;
import com.leilei.service.IUserRoleService;
import com.leilei.service.IUserService;
import com.leilei.util.response.JsonReturn;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : leilei
 * @date : 14:26 2020/3/5
 * @desc : 多数据源事务测试
 */
@RestController
@RequestMapping("ato")
public class AtomikosController {

    @Autowired
    private IAtoService atoService;

    /**
     * 事务测试  同时插入几条数据
     *
     * @param bodyVo
     * @return
     */
    @RequestMapping("/insert")
    public Map<String, Object> insert(@RequestBody BodyVo bodyVo) {
        return atoService.insertAto(bodyVo);
    }

    /**
     * 连表测试
     */
    @RequestMapping("find")
    public JsonReturn find(Long id) {
        return atoService.find(id);
    }
}
