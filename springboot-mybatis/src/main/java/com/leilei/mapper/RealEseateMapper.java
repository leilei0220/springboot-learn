package com.leilei.mapper;

import com.leilei.entity.RealEseate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealEseateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RealEseate record);

    RealEseate selectByPrimaryKey(Long id);

    List<RealEseate> selectAll();

    int updateByPrimaryKey(RealEseate record);

    List<RealEseate> selectAllByQuery(@Param("projectName") String projectName, @Param("address") String address);
}