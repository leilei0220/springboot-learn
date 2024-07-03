package com.leilei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leilei.entity.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lei
 * @create 2024-07-03 16:53
 * @desc
 **/
@Mapper
public interface PersonMapper extends BaseMapper<Person> {
}
