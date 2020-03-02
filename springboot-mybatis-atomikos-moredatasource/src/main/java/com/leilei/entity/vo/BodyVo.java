package com.leilei.entity.vo;

import com.leilei.entity.one.RealEseate;
import com.leilei.entity.two.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 17:58 2020/3/1
 * @desc :
 */
@Data
public class BodyVo implements Serializable {
    private User user;
    private RealEseate realEseate;
}
