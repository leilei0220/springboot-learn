package com.leilei.mapper;

import com.leilei.entity.AuthUser;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 22:38
 */
public interface AuthUserMapper {
    AuthUser findOneByName(String userName);
}
