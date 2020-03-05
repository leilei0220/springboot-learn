package com.leilei.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.leilei.entity.one.User;
import com.leilei.entity.vo.BodyVo;
import com.leilei.mapper.one.UserMapper;
import com.leilei.mapper.three.RoleMapper;
import com.leilei.mapper.two.UserRoleMapper;
import com.leilei.service.IAtoService;
import com.leilei.util.response.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : leilei
 * @date : 14:34 2020/3/5
 * @desc :
 */
@Service
public class IAtoServiceImpl implements IAtoService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional //数据源配置了AtomikosDataSourceBean 再打上此注解即可做到多数据源事务控制
    public Map<String, Object> insertAto(BodyVo bodyVo) {
        userMapper.insert(bodyVo.getUser());
        userRoleMapper.insert(bodyVo.getUserRole());
        int a = 1 / 0; //制造异常
        roleMapper.insert(bodyVo.getRole());
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", bodyVo.getUser());
        map.put("user_role", bodyVo.getUserRole());
        map.put("role", bodyVo.getRole());
        return map;
    }

    @Override
    public JsonReturn find(Long id) {
        //随手测试一下分页
        Page<User> page = new Page<>(1,2);
        IPage<User> iPage = userMapper.MoreDatasourceFindAll(page, id);
        return JsonReturn.buildSuccess(iPage, "Success");
    }


}
