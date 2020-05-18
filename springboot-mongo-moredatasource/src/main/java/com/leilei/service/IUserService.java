package com.leilei.service;

import com.leilei.config.AjaxResult;
import com.leilei.entity.one.User;
import com.leilei.entity.one.vo.CountUser;
import com.leilei.entity.one.vo.MonthByUser;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:17 2020/2/16
 * @desc :
 */
public interface IUserService {

    AjaxResult insertUser(User user) throws Exception;

    List<User> findAll();

    Integer countUserAge();

    MonthByUser countUserByMonth();

    CountUser countUser();

}

