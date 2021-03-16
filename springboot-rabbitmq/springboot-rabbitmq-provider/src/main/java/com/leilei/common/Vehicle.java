package com.leilei.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
}
