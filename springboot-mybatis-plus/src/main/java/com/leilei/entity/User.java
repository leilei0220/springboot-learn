package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author leilei
 * @since 2020-03-02
 */
@Data
public class User extends Model<User> implements Serializable{

    private static final long serialVersionUID=1L;

    /**
     * 用户主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 多对多关联对象属性
     */
    @TableField(exist = false)
    private List<Role> roleList;

    /**
     * 一对多关联对象属性
     */
    @TableField(exist = false)
    private List<RealEseate> realEseateList;


}
