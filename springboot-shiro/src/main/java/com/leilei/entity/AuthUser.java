package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 22:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements Serializable {
    private Long id;
    private String username;
    private String password;

}