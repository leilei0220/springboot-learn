package com.leilei.service;

import com.leilei.entity.Permission;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/1 10:17
 */
public interface IAuthPermissionService {

    List<Permission> findPermissionById(Long userId);

    List<Permission> findAll();
}
