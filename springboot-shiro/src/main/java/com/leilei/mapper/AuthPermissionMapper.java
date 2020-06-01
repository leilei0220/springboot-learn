package com.leilei.mapper;

import com.leilei.entity.Permission;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/1 10:16
 */
public interface AuthPermissionMapper {

    List<Permission> findPermissionById(Long userId);

    List<Permission> findAll();
}
