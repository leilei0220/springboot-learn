package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author leilei
 * @since 2020-03-04
 */
public class Permission extends Model<Permission> {

    private static final long serialVersionUID=1L;

    /**
     * 权限主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 权限标识
     */
    private String permissionSn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionSn() {
        return permissionSn;
    }

    public void setPermissionSn(String permissionSn) {
        this.permissionSn = permissionSn;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Permission{" +
        "id=" + id +
        ", permissionName=" + permissionName +
        ", permissionSn=" + permissionSn +
        "}";
    }
}
