package com.leilei.entity.one.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : leilei
 * @date : 17:46 2020/2/16
 * @desc : 月份计数对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthByUser {

    private Integer January;

    private Integer February;

    private Integer March;

    private Integer April;

    private Integer May;

    private Integer June;

    private Integer July;

    private Integer August;

    private Integer September;

    private Integer October;

    private Integer November;

    private Integer December;
}
