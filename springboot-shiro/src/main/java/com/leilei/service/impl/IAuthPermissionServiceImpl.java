package com.leilei.service.impl;

import com.leilei.entity.Permission;
import com.leilei.mapper.AuthPermissionMapper;
import com.leilei.service.IAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/1 10:17
 */
@Service
public class IAuthPermissionServiceImpl implements IAuthPermissionService {
    @Autowired
    private AuthPermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionById(Long userId) {
        return permissionMapper.findPermissionById(userId);
    }

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }
}
