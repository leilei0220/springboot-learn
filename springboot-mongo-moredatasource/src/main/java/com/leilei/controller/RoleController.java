package com.leilei.controller;

import com.leilei.config.AjaxResult;
import com.leilei.entity.two.Role;
import com.leilei.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public AjaxResult add(Role role) throws Exception {
    return roleService.insertRole(role);

  }

  @GetMapping("/findAll")
  public List<Role> findAll() {
    return roleService.findAll();
  }
}
