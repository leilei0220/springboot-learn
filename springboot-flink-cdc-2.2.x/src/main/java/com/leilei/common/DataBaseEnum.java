package com.leilei.common;

import lombok.Getter;

/**
 * @author lei
 * @create 2022-09-20 14:01
 * @desc 数据库类型枚举
 **/
public enum DataBaseEnum {
    /**
     * MYSQL
     */
    MYSQL(1),

    /**
     * SQL_SERVER
     */
    SQL_SERVER(2),

    ;
    @Getter
    private final int type;

    DataBaseEnum(int type) {
        this.type = type;
    }
}
