package com.leilei.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lei
 * @create 2023-08-18 09:44
 * @desc 定位
 **/
@Data
public class Location {

    private String plate;
    private String color;
    private LocalDateTime sendTime;
    private Integer num;

}
