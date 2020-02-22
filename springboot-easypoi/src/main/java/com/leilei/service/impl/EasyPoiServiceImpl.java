package com.leilei.service.impl;

import com.leilei.entity.DepartMent;
import com.leilei.entity.User;
import com.leilei.entity.UserVo;
import com.leilei.service.IEasyPoiService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : leilei
 * @date : 14:58 2020/2/22
 * @desc :
 */
@Service
public class EasyPoiServiceImpl implements IEasyPoiService {

    public static String HeadImgUrl = "/static/head.jpg";

    @Override
    public List<User> findUserList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            User user = new User();
            user.setUsername("leilei" + i);
            if (i % 2 == 0) {
                user.setSex(true);
                user.setDepartMent(new DepartMent("电商部门"));
            } else {
                user.setSex(false);
                user.setDepartMent(new DepartMent("传统部门"));

            }
            user.setPassword("leilei" + i);
            int age = new Random().nextInt(70 - 35) + 35;
            user.setAge(age);
            user.setHeadimg(HeadImgUrl);
            user.setCreateTime(LocalDateTime.now());
            users.add(user);
        }
        return users;
    }

    @Override
    public List<UserVo> findUserVoList() {
        List<UserVo> users = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            UserVo user = new UserVo();
            user.setUsername("leilei" + i);
            if (i % 2 == 0) {
                user.setSex(true);
                user.setDepartMent(new DepartMent("电商部门"));
            } else {
                user.setSex(false);
                user.setDepartMent(new DepartMent("传统部门"));

            }
            user.setPassword("leilei" + i);
            int age = new Random().nextInt(70 - 35) + 35;
            user.setAge(age);
            user.setHeadimg(HeadImgUrl);
            user.setCreateTime(LocalDateTime.now());
            users.add(user);
        }
        return users;
    }
}
