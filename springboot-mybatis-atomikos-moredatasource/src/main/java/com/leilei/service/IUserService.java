package com.leilei.service;

import com.leilei.entity.two.User;

import java.util.List;

/**
 * @author : leilei
 * @date : 17:07 2020/3/1
 * @desc :
 */
public interface IUserService {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll(Integer page,Integer size);

    int updateByPrimaryKey(User record);
}
