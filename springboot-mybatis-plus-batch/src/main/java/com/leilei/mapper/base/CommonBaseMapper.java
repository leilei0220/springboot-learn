package com.leilei.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lei
 * @create 2021-09-09 10:53
 * @desc 中继Mapper 包含自己批处理方法与MybatisPlus自身通用方法
 **/
public interface CommonBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量新增
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 批量修改 条件为主键
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<T> list);
}