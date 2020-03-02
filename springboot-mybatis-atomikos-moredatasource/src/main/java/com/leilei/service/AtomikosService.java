package com.leilei.service;

import com.leilei.entity.one.RealEseate;
import com.leilei.entity.vo.BodyVo;

import java.util.List;
import java.util.Map;

/**
 * @author : leilei
 * @date : 17:53 2020/3/1
 * @desc :
 */
public interface AtomikosService {
    /**
     * 多数据源事务测试 向不同的数据源中同时插入数据
     */
    Map<String, Object> insertToTwoDatebaseService(BodyVo bodyVo);

    /**
     * 多数据源连表查询
     */
    List<RealEseate> findall(Integer page, Integer size);
}
