package com.leilei.service;

import com.leilei.config.AjaxResult;
import com.leilei.entity.two.Role;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:17 2020/2/16
 * @desc :
 */
public interface IRoleService {

    AjaxResult insertRole(Role role) throws Exception;

    List<Role> findAll();
}
