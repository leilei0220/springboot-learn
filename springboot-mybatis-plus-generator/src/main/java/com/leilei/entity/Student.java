package com.leilei.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <b> Student实体类
 * <br>Date: 2020-07-20
 * <br>Author : leilei
 */
@Data
@Accessors(chain = true)
public class Student extends Model<Student> implements Serializable{

private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
        @TableId(value="id", type= IdType.AUTO)
                private Long id;

    /**
     * 名字
     */
            private String name;

    /**
     * 年龄
     */
            private Integer age;

    /**
     * 是否删除
     */
        
    @TableLogic
    private Boolean delFlag;

    /**
     * 版本号
     */
        @Version
        private Integer version;

}