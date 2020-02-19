package com.leilei.controller;

import com.leilei.entity.two.Role;
import com.leilei.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : leilei
 * @date : 16:22 2020/2/16
 * @desc :
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping("add")
    public String add(Role role) {

        int i = roleService.insertRole(role);
        if (i == 1) {
            return "success";
        }
        return "false";
    }
}
