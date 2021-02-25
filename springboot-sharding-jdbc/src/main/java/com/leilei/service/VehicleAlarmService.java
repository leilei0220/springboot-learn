package com.leilei.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.leilei.mapper.VehicleAlarmMapper;
import com.leilei.entity.VehicleAlarm;
/**
 * @author lei
 * @desc
 * @version 1.0
 * @date 2021-02-25 17:37
 */
@Service
public class VehicleAlarmService{

    @Resource
    private VehicleAlarmMapper vehicleAlarmMapper;


    public VehicleAlarm selectByPrimaryKey(Integer id) {
        return vehicleAlarmMapper.selectByPrimaryKey(id);
    }





}
