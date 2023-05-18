package com.leilei.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/14 21:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle implements Serializable {
    private Integer id;
    private String name;
    private LocalDateTime sendDate;

    public Vehicle(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.setSendDate(LocalDateTime.now());
    }

}
