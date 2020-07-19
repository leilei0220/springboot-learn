package com.leilei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leilei.entity.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

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

    /**
     * 注解方式  连表查询
     * @param userId
     * @return
     */
    @Select("select * from user where id= #{userId}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password"),
            //@many注解的select属性表示需要关联执行的SQL语句
            //FetchType.LAZY表示查询的类型是延迟加载
            //id 则是选择以当前哪一个字段作为条件参数 传递给另一个查询方法
            @Result(column = "id",property = "realEseateList",many = @Many(select = "com.leilei.mapper.RealEseateMapper.findByUser",fetchType= FetchType.EAGER)),
    })
    User selectOneByAnon(Long userId);
}
