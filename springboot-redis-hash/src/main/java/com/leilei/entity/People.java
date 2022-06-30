package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;


/**
 * @author lei
 * @create 2022-04-25 11:31
 * @desc
 **/
@RedisHash
@Data
@NoArgsConstructor
@AllArgsConstructor
public class People {
    @Id
    private Integer id;

    @Indexed
    private String name;

    private List<String> hobby;

    @TimeToLive
    private Long expiration;

    private Point point;
}
