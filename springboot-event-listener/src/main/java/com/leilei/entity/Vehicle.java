package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("vehicle")
@Data
public class Vehicle {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String plate;
    private String color;

    public Vehicle(String plate, String color) {
        this.plate = plate;
        this.color = color;
    }

    public Vehicle() {
    }
}
