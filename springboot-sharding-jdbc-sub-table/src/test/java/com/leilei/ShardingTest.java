package com.leilei;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.entity.VehicleAlarm;
import com.leilei.mapper.VehicleAlarmMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        for (int i = 1; i < 20; i++) {
//            vehicleAlarm.setId(String.valueOf(i));
            vehicleAlarm.setLicensePlate("川E0000" + i);
            vehicleAlarm.setPlateColor("黄");
            vehicleAlarm.setZone(i % 2 == 0 ? "sc" : "bj");
            vehicleAlarm.setDeviceTime(i % 2 == 0 ? System.currentTimeMillis() : 1611827071000L);
            vehicleAlarmMapper.insert(vehicleAlarm);
        }
    }

    @Test
    public void testIFindAlarm() {
        LambdaQueryWrapper<VehicleAlarm> wrapper = new QueryWrapper<VehicleAlarm>().lambda();
        wrapper.between(VehicleAlarm::getDeviceTime, 1611827071000L,1614517065621L);
        List<VehicleAlarm> vehicleAlarms = vehicleAlarmMapper.selectList(wrapper);
        System.out.println(vehicleAlarms);
    }



}
