package com.leilei.entity;

import lombok.Data;

/**
 * @author lei
 * @version 1.0
 * @date 2020/6/1 10:14
 */
@Data
public class Permission {
    /**主键*/
    private Long id;
    /**标识*/
    private String sn;
    /**url*/
    private String url;
    /**权限名*/
    private String name;
}
