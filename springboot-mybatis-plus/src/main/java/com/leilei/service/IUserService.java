package com.leilei.service;

import com.leilei.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
public interface IUserService extends IService<User> {

    User selectOneDetail(Long uid);

    User selectOneDetailByReal(Long userid);
}

