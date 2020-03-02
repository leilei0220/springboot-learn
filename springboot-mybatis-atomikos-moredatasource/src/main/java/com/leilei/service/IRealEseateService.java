package com.leilei.service;

import com.leilei.entity.one.RealEseate;

import java.util.List;

/**
 * @author : leilei
 * @date : 17:08 2020/3/1
 * @desc :
 */
public interface IRealEseateService {
    int deleteByPrimaryKey(Long id);

    int insert(RealEseate record);

    RealEseate selectByPrimaryKey(Long id);

    List<RealEseate> selectAll();

    int updateByPrimaryKey(RealEseate record);
}
