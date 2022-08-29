package com.leilei.mysql;

import lombok.Getter;

/**
 * @author lei
 * @create 2022-08-29 14:32
 * @desc 操作类型枚举
 **/
public enum OperatorTypeEnum {
    /**
     * 新增
     */
    INSERT(1),
    /**
     * 修改
     */
    UPDATE(2),
    /**
     * 删除
     */
    DELETE(3),
    ;

    @Getter
    private final int type;

    OperatorTypeEnum(int type) {
        this.type = type;
    }
}
