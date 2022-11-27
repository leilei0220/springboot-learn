package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author lei
 * @create 2022-11-27 19:04
 * @desc
 **/
@Getter
@Setter
public class Vehicle extends ApplicationEvent  {
    private Integer id;
    private String plate;
    private String color;


    public Vehicle(Object source, Integer id, String plate, String color) {
        super(source);
        this.id = id;
        this.plate = plate;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", color='" + color + '\'' +
                ", source=" + source +
                '}';
    }
}
