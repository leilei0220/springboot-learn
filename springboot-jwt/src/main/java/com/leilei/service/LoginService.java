package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.common.AjaxResult;
import com.leilei.jwt.JWTSupport;
import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 16:20
 * @desc
 */
@Service
public class LoginService {
    private final UserMapper userMapper;
    private final JWTSupport jwtSupport;

    @Autowired
    public LoginService(UserMapper userMapper,JWTSupport jwtSupport) {
        this.userMapper = userMapper;
        this.jwtSupport = jwtSupport;
    }

    public AjaxResult login(User user) {
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword()));
        if (existUser == null) {
            return AjaxResult.error("账户或密码错误");
        }
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("account", existUser.getAccount());
        map.put("id", existUser.getId().toString());
        return AjaxResult.success(jwtSupport.buildToken(map));
    }
}
