package com.leilei.entity;

/**
 * @author lei
 * @desc
 * @version 1.0
 * @date 2021-02-25 17:37
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * vehicle_alarm
 */
@Data
@TableName("vehicle_alarm")
public class VehicleAlarm {
    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String  id;

    /**
     * licensePlate
     */
    private String licensePlate;

    /**
     * plateColor
     */
    private String plateColor;

    /**
     * deviceTime
     */
    private Long deviceTime;

}