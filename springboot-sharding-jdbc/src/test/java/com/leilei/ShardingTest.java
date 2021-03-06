package com.leilei;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.algorithm.DbSelectUtil;
import com.leilei.entity.VehicleAlarm;
import com.leilei.mapper.VehicleAlarmMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/2/25 17:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringbootShardingJdbcApplication.class})
public class ShardingTest {
    @Autowired
    private VehicleAlarmMapper vehicleAlarmMapper;

    @Test
    public void testInsertAlarm() {
        VehicleAlarm vehicleAlarm = new VehicleAlarm();
        long[] timeArr = {1611827071123L, 1612274617000L, 1614693856000L};
        for (int i = 1; i < 20; i++) {
            vehicleAlarm.setLicensePlate("川E" + System.currentTimeMillis()/123456789);
            vehicleAlarm.setPlateColor("黄");
            vehicleAlarm.setZone(i % 2 == 0 ? "sc" : "bj");
            vehicleAlarm.setDeviceTime(timeArr[(int) (Math.random() * 3)]);
            vehicleAlarmMapper.insert(vehicleAlarm);
        }
    }

    @Test
    public void testEq() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        wrapper.eq(VehicleAlarm::getZone, "bj").eq(VehicleAlarm::getDeviceTime,1612274617000L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        System.out.println(vehicleAlarms);
    }

    /**
     * > 查询会报错 TODO
     */
    @Test
    public void testLtGt() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        wrapper.lt(VehicleAlarm::getDeviceTime, 1614517065621L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        System.out.println(vehicleAlarms);
    }

    @Test
    public void testTableBetween() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        wrapper.between(VehicleAlarm::getDeviceTime, 1611827071000L, 1614517065621L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        System.out.println(vehicleAlarms);
    }

    /**
     * 范围查询 库 与表
     */
    @Test
    public void testTableDbBetween1() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        List<String> zoneList = Arrays.asList("bj","sc");
        DbSelectUtil.DB_SELECTOR.set(zoneList);
        wrapper.between(VehicleAlarm::getZone, "bj","sc")
                .eq(VehicleAlarm::getDeviceTime,  1614517065621L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        DbSelectUtil.DB_SELECTOR.remove();
        System.out.println(vehicleAlarms);
    }

    /**
     * 范围查询库，精准匹配表
     */
    @Test
    public void testTableDbBetween2() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        List<String> zoneList = Arrays.asList("bj","sc");
        DbSelectUtil.DB_SELECTOR.set(zoneList);
        wrapper.in(VehicleAlarm::getZone, zoneList)
                .eq(VehicleAlarm::getDeviceTime,  1614517065621L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        DbSelectUtil.DB_SELECTOR.remove();
        System.out.println(vehicleAlarms);
    }

    /**
     * 与sharding-jdbc 整合后后 分页失效了，需要自己实现分页
     */
    @Test
    public void testPage() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        List<String> zoneList = Arrays.asList("bj","sc");
        DbSelectUtil.DB_SELECTOR.set(zoneList);
        wrapper.in(VehicleAlarm::getZone, zoneList)
                .between(VehicleAlarm::getDeviceTime,1614517065621L,System.currentTimeMillis());
        Integer count = vehicleAlarmMapper.selectCount(wrapper);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper.last("limit 0,10"));
        DbSelectUtil.DB_SELECTOR.remove();
        System.out.println(count);
        System.out.println(vehicleAlarms);
    }




}
