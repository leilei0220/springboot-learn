package com.leilei.controller;

import com.leilei.entity.VehicleAlarm;
import com.leilei.service.VehicleAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @version 1.0
 * @desc
 * @date 2021-02-25 17:19
 */
@RequestMapping("/alarm")
@RestController
public class AlarmController {

    private final VehicleAlarmService vehicleAlarmService;
    @Autowired
    public AlarmController(VehicleAlarmService vehicleAlarmService) {
        this.vehicleAlarmService = vehicleAlarmService;
    }
    @GetMapping("/{id}")
    public VehicleAlarm getOne(@PathVariable("id") Integer id) {
        return vehicleAlarmService.selectByPrimaryKey(id);
    }
}
