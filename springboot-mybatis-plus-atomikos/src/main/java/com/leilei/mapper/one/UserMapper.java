package com.leilei.mapper.one;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leilei.entity.one.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leilei
 * @since 2020-03-05
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> MoreDatasourceFindAll(Page<User> page, @Param("id") Long id);

}
