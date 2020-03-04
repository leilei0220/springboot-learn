package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leilei.entity.RealEseate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leilei
 * @since 2020-03-03
 */
public interface IRealEseateService extends IService<RealEseate> {

    IPage selectJoinTablePage(Integer page, Integer size, String query);
}
