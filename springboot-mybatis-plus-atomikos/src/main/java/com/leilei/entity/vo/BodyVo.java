package com.leilei.entity.vo;

import com.leilei.entity.one.User;
import com.leilei.entity.three.Role;
import com.leilei.entity.two.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 14:31 2020/3/5
 * @desc :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyVo implements Serializable {
    private User user;
    private UserRole userRole;
    private Role role;
}
