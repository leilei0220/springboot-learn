package com.leilei.service.impl;

import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import com.leilei.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectOneDetail(Long userid) {
        return userMapper.selectOneDetail(userid);
    }

    @Override
    public User selectOneDetailByReal(Long userid) {
        return userMapper.selectOneDetailByReal(userid);
    }

}
