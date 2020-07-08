package com.leilei.controller;

import lombok.Builder;
import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/8 21:45
 */
@Data
@Builder
public class User {
    private Long id;
    private String name;
    private Integer age;
}
