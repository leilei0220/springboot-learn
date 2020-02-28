package com.leilei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leilei.entity.RealEseate;
import com.leilei.mapper.RealEseateMapper;
import com.leilei.service.IRealEseateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : leilei
 * @date : 16:08 2020/2/24
 * @desc :
 */
@Service
public class IRealEseateServiceImpl implements IRealEseateService {
    @Autowired
    private RealEseateMapper realEseateMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(RealEseate record) {
        return 0;
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

    /**
     *
     * @param page
     * @param size
     * @return 返回分页对象 （数据详细 当前页 总条数 下一页等各种信息
     * 也可以直接 return realEseateMapper.selectAll(); 将分页好的数据返回  但是不会有其他 下一页 以及总条数 那些信息 仅仅返当前分页条件下展示的几条数据
     */
    @Override
    public PageInfo<RealEseate> selectByPage(Integer page, Integer size) {
        //开启分页
        PageHelper.startPage(page, size);
        //pagehelper 排序功能
        PageHelper.orderBy("build_time desc");
        List<RealEseate> realEseates = realEseateMapper.selectAll();
        PageInfo<RealEseate> realEseatePageInfo = new PageInfo<>(realEseates);
        return realEseatePageInfo;
        //也可以直接返回

    }

    /**
     *  多条件分页查询
     * @param page
     * @param size
     * @param projectName
     * @param address
     * @return
     */
    @Override
    public PageInfo<RealEseate> selectByQuery(Integer page, Integer size, String projectName, String address) {
        PageHelper.startPage(page, size);
       List<RealEseate> realEseates= realEseateMapper.selectAllByQuery(projectName, address);
        return new PageInfo<RealEseate>(realEseates);
    }
}
