package com.leilei.service;

import com.leilei.entity.Vehicle;
import com.sun.applet2.preloader.event.ApplicationExitEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author lei
 * @create 2022-11-27 19:06
 * @desc 车辆事件发布
 **/
@Service
@RequiredArgsConstructor
public class VehiclePublishService {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 普通事件发布
     *
     * @param
     * @return void
     * @author lei
     * @date 2022-11-27 19:28:44
     */
    public void  pushVehicleRegister(Integer id) {
        Vehicle vehicle = new Vehicle("aa", id, "川A00001", "黄");
        eventPublisher.publishEvent(vehicle);
    }

    /**
     * 监听事件，指明监听子类为 Vehicle，且设置条件
     *
     * @param vehicle
     * @return void
     * @author lei
     * @date 2022-11-27 19:30:04
     */
    @EventListener(classes = Vehicle.class,condition ="#vehicle.id.equals(1)")
    public void vehicleEvent(Vehicle vehicle) {
        long timestamp = vehicle.getTimestamp();
        System.out.println(vehicle);
    }
}
