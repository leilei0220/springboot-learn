package com.leilei.service.impl;

import com.github.pagehelper.PageHelper;
import com.leilei.entity.one.RealEseate;
import com.leilei.entity.vo.BodyVo;
import com.leilei.mapper.one.RealEseateMapper;
import com.leilei.mapper.two.UserMapper;
import com.leilei.service.AtomikosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : leilei
 * @date : 17:55 2020/3/1
 * @desc :  测试插入两条数据
 */
@Service
public class AtomikosServiceImpl implements AtomikosService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RealEseateMapper realEseateMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { java.lang.RuntimeException.class })
    public Map<String, Object> insertToTwoDatebaseService(BodyVo bodyVo) {
        userMapper.insert(bodyVo.getUser());
        //故意制造异常
        int a = 1 / 0;
        bodyVo.getRealEseate().setBuildTime(new Date());
        realEseateMapper.insert(bodyVo.getRealEseate());
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", bodyVo.getUser());
        map.put("realReseate", bodyVo.getRealEseate());
        return map;
    }

    @Override
    public List<RealEseate> findall(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        PageHelper.orderBy("build_time desc");
        return realEseateMapper.findMoreDatasource();
    }
}
