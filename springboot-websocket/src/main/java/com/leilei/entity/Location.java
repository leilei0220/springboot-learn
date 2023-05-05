package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @create 2023-05-05 10:56
 * @desc 定位
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String userId;
    private Integer longitude;
    private Integer latitude;
}
