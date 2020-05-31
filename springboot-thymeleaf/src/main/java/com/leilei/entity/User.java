package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 17:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Boolean sex;
    private LocalDateTime birthDay;
}
