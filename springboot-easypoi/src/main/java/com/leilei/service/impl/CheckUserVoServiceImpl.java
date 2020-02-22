package com.leilei.service.impl;

import com.leilei.entity.DepartMent;
import com.leilei.entity.UserVo;
import com.leilei.service.ICheckUserVoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 15:51 2020/2/22
 * @desc : 校验用户是否重复
 */
@Service
public class CheckUserVoServiceImpl implements ICheckUserVoService {
    @Override
    public UserVo findOneByname(String username) {
        /**
         * 虚假数据  由于我这里是根据名字查  那么我模拟一个重名的对象返回即可 （实际开发可根据身份证去数据库查询）
         */
        if (username .equals("leilei0") ) {
            return new UserVo("leilei0", "1", true, 22, "aaa", LocalDateTime.now(), new DepartMent("dianshan"));
        }
        return null;
    }
}
