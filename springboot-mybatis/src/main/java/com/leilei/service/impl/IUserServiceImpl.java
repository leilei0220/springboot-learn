package com.leilei.service.impl;

import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import com.leilei.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:27 2020/2/24
 * @desc :
 */
@Service
@Transactional
public class IUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(User user) {
//        int a = 1 / 0;
        return userMapper.insert(user);
    }

    @Override
    public Integer insertMore(List<User> userList) {
        return userMapper.insertMore(userList);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @Override
    public Integer rmMore(List<Long> ids) {
        return userMapper.rmMore(ids);
    }
}
