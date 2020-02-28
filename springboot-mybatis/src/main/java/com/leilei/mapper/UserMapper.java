package com.leilei.mapper;

import com.leilei.entity.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    Integer insertMore(List<User> list);

    Integer rmMore(List<Long> ids);
}