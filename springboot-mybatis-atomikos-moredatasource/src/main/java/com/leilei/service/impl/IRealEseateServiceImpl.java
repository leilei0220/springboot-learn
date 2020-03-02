package com.leilei.service.impl;

import com.leilei.entity.one.RealEseate;
import com.leilei.mapper.one.RealEseateMapper;
import com.leilei.service.IRealEseateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : leilei
 * @date : 17:09 2020/3/1
 * @desc :
 */
@Service
@Transactional
public class IRealEseateServiceImpl implements IRealEseateService {
    @Autowired
    private RealEseateMapper realEseateMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(RealEseate record) {
        //制造异常 测试仅操作第一个数据源的情况
        int a = 1 / 0;
        record.setBuildTime(new Date());
        return realEseateMapper.insert(record);
    }

    @Override
    public RealEseate selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<RealEseate> selectAll() {
        return realEseateMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(RealEseate record) {
        return 0;
    }
}
