package com.leilei.service.impl;

import com.leilei.entity.AuthUser;
import com.leilei.mapper.AuthUserMapper;
import com.leilei.service.IAuthUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 23:30
 */
@Service
public class IAuthUserServiceImpl implements IAuthUserService {
    @Autowired
    private AuthUserMapper authUserMapper;
    @Override
    public AuthUser findOneByName(String userName) {
        return authUserMapper.findOneByName(userName);
    }

}
