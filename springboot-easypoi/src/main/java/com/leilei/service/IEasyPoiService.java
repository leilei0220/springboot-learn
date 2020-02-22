package com.leilei.service;

import com.leilei.entity.User;
import com.leilei.entity.UserVo;

import java.util.List;

/**
 * @author : leilei
 * @date : 14:58 2020/2/22
 * @desc :
 */
public interface IEasyPoiService {
    /**
     * 查询用户列表
     * @return
     */
    List<User> findUserList();

    List<UserVo> findUserVoList();
}

