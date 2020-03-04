package com.leilei.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leilei.entity.RealEseate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leilei
 * @since 2020-03-03
 */
public interface RealEseateMapper extends BaseMapper<RealEseate> {

    IPage selectJoinTablePage(Page<RealEseate> realEseatePage, @Param("query") String query);
}
