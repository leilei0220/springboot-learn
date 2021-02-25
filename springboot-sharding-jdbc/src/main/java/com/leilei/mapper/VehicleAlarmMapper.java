package com.leilei.mapper;

import com.leilei.entity.VehicleAlarm;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lei
 * @desc
 * @version 1.0
 * @date 2021-02-25 17:37
 */
@Mapper
public interface VehicleAlarmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VehicleAlarm record);

    int insertSelective(VehicleAlarm record);

    VehicleAlarm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VehicleAlarm record);

    int updateByPrimaryKey(VehicleAlarm record);
}