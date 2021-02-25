package com.leilei;

import com.leilei.entity.VehicleAlarm;
import com.leilei.mapper.VehicleAlarmMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testInsertOrder(){
            VehicleAlarm vehicleAlarm = new VehicleAlarm();
        for(int i=1;i<20;i++){
            vehicleAlarm.setLicensePlate("川A0000" + i);
            vehicleAlarm.setPlateColor("黄");
            vehicleAlarm.setDeviceTime(System.currentTimeMillis());
            vehicleAlarmMapper.insert(vehicleAlarm);
        }
    }

}
