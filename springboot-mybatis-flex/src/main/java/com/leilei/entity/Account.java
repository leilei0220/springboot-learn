package com.leilei.entity;

import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.mask.Masks;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Table("tb_account")
public class Account {

    @Id(keyType = KeyType.Auto)
    private Long id;
    @ColumnMask(Masks.CHINESE_NAME)
    private String userName;
    private Integer age;
    private Date birthday;

}