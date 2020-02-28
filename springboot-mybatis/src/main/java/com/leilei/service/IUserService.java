package com.leilei.service;

import com.leilei.entity.User;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:27 2020/2/24
 * @desc :
 */
public interface IUserService {
    int deleteByPrimaryKey(Long id);

    int insert(User user);

    Integer insertMore(List<User> userList);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User user);

    Integer rmMore(List<Long> ids);
}
