package com.leilei.mapper.one;

import com.leilei.entity.one.RealEseate;

import java.util.List;

public interface RealEseateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RealEseate record);

    RealEseate selectByPrimaryKey(Long id);

    List<RealEseate> selectAll();

    int updateByPrimaryKey(RealEseate record);

    /**
     * 跨数据源连表查询
     */
    List<RealEseate> findMoreDatasource();
}