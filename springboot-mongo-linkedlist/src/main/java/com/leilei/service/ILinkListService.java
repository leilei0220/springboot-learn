package com.leilei.service;

import com.leilei.util.response.JsonReturn;

/**
 * @author : leilei
 * @date : 11:30 2020/3/15
 * @desc :
 */
public interface ILinkListService {
    JsonReturn addData();

    JsonReturn MoreToOne(Long studentId, Long classId);

    JsonReturn oneToMany();

    JsonReturn moreTableOneToOne();

}
