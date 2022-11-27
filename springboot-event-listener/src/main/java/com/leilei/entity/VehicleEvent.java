package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author lei
 * @create 2022-11-27 20:12
 * @desc
 **/
@Getter
@Setter
public class VehicleEvent extends ApplicationEvent {
    private Integer id;
    private String plate;
    private String color;

    public  VehicleEvent(Object source,Vehicle vehicle) {
        super(source);
        this.id = vehicle.getId();
        this.plate = vehicle.getPlate();
        this.color = vehicle.getColor();
    }
}
