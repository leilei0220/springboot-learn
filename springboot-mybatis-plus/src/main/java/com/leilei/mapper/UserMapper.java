package com.leilei.mapper;

import com.leilei.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
public interface UserMapper extends BaseMapper<User> {

    User selectOneDetail(Long uid);

    User selectOneDetailByReal(Long userid);
}
