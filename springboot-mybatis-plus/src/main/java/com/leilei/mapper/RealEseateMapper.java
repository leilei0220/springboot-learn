package com.leilei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leilei.entity.RealEseate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select * from real_eseate where user_id= #{id}")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "project_name",property = "projectName"),
            @Result(column = "address",property = "address"),
            @Result(column = "house_type",property = "houseType"),
            @Result(column = "area",property = "area"),
            @Result(column = "build_time",property = "buildTime"),
    })
    List<RealEseate> findByUser(Long id);
}
