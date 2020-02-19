package com.leilei.entity.one.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : leilei
 * @date : 18:16 2020/2/16
 * @desc : 当天 本周 本月 本季节 本年 总
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountUser {
    /**
     * 今日
     */

    private Integer dayCount;

    /**
     * 本周
     */

    private Integer weekCount;

    /**
     * 本月
     */

    private Integer monthCount;

    /**
     * 本季
     */

    private Integer quarterCount;
    /**
     * 本年
     */

    private Integer yearCount;
    /**
     * 总
     */

    private Integer totalCount;
}
