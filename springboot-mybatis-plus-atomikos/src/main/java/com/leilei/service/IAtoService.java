package com.leilei.service;

import com.leilei.entity.vo.BodyVo;
import com.leilei.util.response.JsonReturn;

import java.util.Map;

/**
 * @author : leilei
 * @date : 14:34 2020/3/5
 * @desc :
 */
public interface IAtoService {
    Map<String, Object> insertAto(BodyVo bodyVo);

    JsonReturn find(Long id);
}
