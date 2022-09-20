package com.leilei.strategy.entity;

import lombok.Data;

/**
 * @author lei
 * @create 2022-09-20 14:40
 * @desc 因历史原因, 原系统老表都是大驼峰
 **/
@Data
public class GpsVehicleDriverBind {
    private Integer VehicleID;
    private Integer DriverID;
    private Integer Status;
}
