package com.leilei.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leilei.entity.RealEseate;
import com.leilei.mapper.RealEseateMapper;
import com.leilei.service.IRealEseateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author leilei
 * @since 2020-03-03
 */
@Service
public class RealEseateServiceImpl extends ServiceImpl<RealEseateMapper, RealEseate> implements IRealEseateService {
    @Autowired
    private RealEseateMapper realEseateMapper;

    @Override
    public IPage selectJoinTablePage(Integer page, Integer size, String query) {
        Page<RealEseate> realEseatePage = new Page<>(page, size);

        return realEseateMapper.selectJoinTablePage(realEseatePage, query);
    }
}
