package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/7 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String username;
    private String nick;
    private Integer age;
}
