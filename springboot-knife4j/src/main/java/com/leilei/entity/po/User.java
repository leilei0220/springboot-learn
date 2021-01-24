package com.leilei.entity.po;

import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:37
 */
@Data
public class User {
    private Long id;
    private String name;
    private String nickName;
    private String email;
    private Integer version;
}
