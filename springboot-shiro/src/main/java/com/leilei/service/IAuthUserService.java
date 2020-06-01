package com.leilei.service;

import com.leilei.entity.AuthUser;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 23:29
 */
public interface IAuthUserService {
    AuthUser findOneByName(String userName);
}
