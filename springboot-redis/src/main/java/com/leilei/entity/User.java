package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 15:52 2020/2/18
 * @desc : 对象转JSON
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private String username;
    private String password;
    private Integer age;


}
