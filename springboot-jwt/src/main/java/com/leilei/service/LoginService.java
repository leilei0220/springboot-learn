package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.common.AjaxResult;
import com.leilei.entity.User;
import com.leilei.entity.UserSub;
import com.leilei.jwt.JwtSupport;
import com.leilei.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 16:20
 * @desc
 */
@Service
public class LoginService {
    private final UserMapper userMapper;
    private final JwtSupport jwtSupport;

    @Autowired
    public LoginService(UserMapper userMapper, JwtSupport jwtSupport) {
        this.userMapper = userMapper;
        this.jwtSupport = jwtSupport;
    }

    public AjaxResult<String> login(User user) {
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword()));
        if (existUser == null) {
            return AjaxResult.error("账户或密码错误");
        }
        UserSub userSub = new UserSub();
        BeanUtils.copyProperties(existUser, userSub);
        return AjaxResult.success(jwtSupport.buildToken(userSub));
    }
}
