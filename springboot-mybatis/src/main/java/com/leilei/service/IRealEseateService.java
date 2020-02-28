package com.leilei.service;

import com.github.pagehelper.PageInfo;
import com.leilei.entity.RealEseate;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:08 2020/2/24
 * @desc :
 */
public interface IRealEseateService {
    int deleteByPrimaryKey(Long id);

    int insert(RealEseate record);

    RealEseate selectByPrimaryKey(Long id);

    List<RealEseate> selectAll();

    int updateByPrimaryKey(RealEseate record);

    PageInfo<RealEseate> selectByPage(Integer page, Integer size);

    PageInfo<RealEseate> selectByQuery(Integer page, Integer size, String projectName, String address);
}

