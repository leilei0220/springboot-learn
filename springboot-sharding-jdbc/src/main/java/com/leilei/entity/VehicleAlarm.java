package com.leilei.entity;

/**
 * @author lei
 * @desc
 * @version 1.0
 * @date 2021-02-25 17:37
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2021/2/28 16:49
 */
@Data
@TableName("vehicle_alarm")
public class VehicleAlarm {
    /**
     * id
     */
//    @TableId(type = IdType.INPUT,value = "id")
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

    private String zone;

}