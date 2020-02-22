package com.leilei.service;

import com.leilei.entity.UserVo;

/**
 * @author : leilei
 * @date : 15:51 2020/2/22
 * @desc : 自定义校验规则接口
 */
public interface ICheckUserVoService {
    UserVo findOneByname(String username);
}
