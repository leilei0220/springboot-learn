package com.leilei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author leilei
 * @since 2020-08-02
 */
public class Class extends Model<Class> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班级名
     */
    private String name;

    /**
     * 班级人数
     */
    private String number;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long modifyTime;

    /**
     * 修改者ID
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer modifyId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyId() {
        return modifyId;
    }

    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Class{" +
        "id=" + id +
        ", name=" + name +
        ", number=" + number +
        ", createTime=" + createTime +
        ", createId=" + createId +
        ", modifyTime=" + modifyTime +
        ", modifyId=" + modifyId +
        "}";
    }
}
