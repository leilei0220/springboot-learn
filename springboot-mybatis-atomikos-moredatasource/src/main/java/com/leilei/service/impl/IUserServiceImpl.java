package com.leilei.service.impl;

import com.github.pagehelper.PageHelper;
import com.leilei.entity.two.User;
import com.leilei.mapper.two.UserMapper;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : leilei
 * @date : 17:07 2020/3/1
 * @desc :
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        //单个数据源二测试事务
        userMapper.insert(new User("test", "12345"));
        int a = 1 / 0;
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<User> selectAll(Integer page,Integer size) {
        PageHelper.startPage(page, size);
        return userMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
