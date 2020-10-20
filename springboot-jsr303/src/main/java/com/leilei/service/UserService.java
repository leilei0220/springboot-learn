package com.leilei.service;

import com.leilei.common.AjaxResult;
import com.leilei.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @version 1.0
 * @date 2020-10-20 12:35
 * @desc
 */
@Component
public class UserService {

    public AjaxResult addUser(User user) {
        return AjaxResult.success(user);
    }

}
