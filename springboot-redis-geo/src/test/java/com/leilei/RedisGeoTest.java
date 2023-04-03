package com.leilei;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 * @create 2023-04-03 10:41
 * @desc
 **/
@SpringBootTest(classes = RedisGeoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class RedisGeoTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void test() {
        // 内部使用的 zet结构
        redisTemplate.opsForGeo().add("geo", new Point(104.063037, 30.701011), "九里堤");
        redisTemplate.opsForGeo().add("geo", new Point(104.072595, 30.700887), "北站西");


        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("九里堤", new Point(104.063037, 30.701011)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("北站西", new Point(104.072595, 30.700887)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("九里堤农贸", new Point(104.064726, 30.699334)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("西北桥", new Point(104.064007, 30.692037)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("城北派出所", new Point(104.073386, 30.702136)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("人民北路", new Point(104.078345, 30.692168)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("哈啰酒店-成都西南交大店", new Point(104.052828, 30.699939)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("矮冬瓜红茶火锅", new Point(104.052936, 30.698899)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("沈跃全中医", new Point(104.052199, 30.698231)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("五丁小学", new Point(104.067169, 30.695763)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("那屋网客", new Point(104.061456, 30.700576)));

        // 获取指定元素的经纬度信息
        List<Point> position = redisTemplate.opsForGeo().position("geo", "九里堤", "北站西");
        System.out.println(position);
        // 获取指定两个元素间的距离 默认是m单位
        Distance distance = redisTemplate.opsForGeo().distance("geo", "九里堤", "北站西");
        Distance distance2 = redisTemplate.opsForGeo().distance("geo", "九里堤", "北站西", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance.getValue() + " " + distance.getUnit());
        System.out.println(distance2.getValue() + " " + distance2.getUnit());

        // 以指定元素为圆心，获取指定半径范围内的数据 （包含自身）
        GeoResults<RedisGeoCommands.GeoLocation<Object>> radius = redisTemplate.opsForGeo().radius("geo", "九里堤", 1000);
        System.out.println(new ArrayList<>(radius.getContent()));


        // 定制化获取指定半径内的数据
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
        // 只获取最近的一个geo信息
        geoRadiusCommandArgs.limit(1);
        geoRadiusCommandArgs.includeCoordinates();
        // 距给定经纬度由远及近的顺序
        geoRadiusCommandArgs.sort(Sort.Direction.DESC);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> radius2 = redisTemplate.opsForGeo().radius("geo", "九里堤",
                new Distance(1, RedisGeoCommands.DistanceUnit.KILOMETERS), geoRadiusCommandArgs);
        System.out.println(new ArrayList<>(radius2.getContent()));

        // 这个要redis版本 >= 6.2.0
        // GeoResults geoResults = redisTemplate.opsForGeo().search("geo", new GeoReference.GeoCoordinateReference<>(104.061456, 30.700576), new Distance(500, RedisGeoCommands.DistanceUnit.METERS));
        // System.out.println(geoResults.getContent());
    }
}
