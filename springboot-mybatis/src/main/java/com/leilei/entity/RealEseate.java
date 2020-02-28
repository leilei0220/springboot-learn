package com.leilei.entity;

import lombok.Data;

import java.util.Date;
/**
 * @author leilei
 */
@Data
public class RealEseate {
    private Long id;

    private String projectName;

    private String address;

    private String houseType;

    private Integer area;

    private Date buildTime;

    private Long userId;

    private User user;
}