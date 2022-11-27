package com.leilei.service;

import com.leilei.entity.Vehicle;
import com.leilei.entity.VehicleEvent;
import com.leilei.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author lei
 * @create 2022-11-27 19:06
 * @desc 车辆事件发布
 **/
@Service
@RequiredArgsConstructor
@Log4j2
public class VehicleService {

    private final ApplicationEventPublisher eventPublisher;
    private final VehicleMapper vehicleMapper;

    /**
     * 普通事件发布
     *
     * @param
     * @return void
     * @author lei
     * @date 2022-11-27 19:28:44
     */
    @Transactional(rollbackFor = Exception.class)
    public void pushVehicleRegister() {
        Vehicle vehicle = new Vehicle( "川A00001-translation", "黄");
        vehicleMapper.insert(vehicle);
        eventPublisher.publishEvent(new VehicleEvent("aa", vehicle));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("注册完成");
        // throw new RuntimeException("aaa");
    }

    /**
     * 监听事件，指明监听子类为 Vehicle，且设置条件
     * 此种方式无法感知到事务是否已提交
     * @param vehicle
     * @return void
     * @author lei
     * @date 2022-11-27 19:30:04
     */
    @EventListener(classes = VehicleEvent.class /*condition = "#vehicle.id.equals(1)"*/)
    public void vehicleEvent(VehicleEvent vehicle) {
        Vehicle vehicle1 = vehicleMapper.selectById(vehicle.getId());
        log.info("接收到数据：{}", vehicle);
        log.info("数据库查询的数据：{}", vehicle1);
    }

    /**
     * 带事务的监听器,可选择收到指定事务类型后触发监听
     *
     * @param vehicle
     * @return void
     * @author lei
     * @date 2022-11-27 21:53:57
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = VehicleEvent.class)
    public void translationVehicleEvent(VehicleEvent vehicle) {
        Vehicle vehicle1 = vehicleMapper.selectById(vehicle.getId());
        log.info("事务事件监听-接收到数据：{}", vehicle);
        log.info("事务事件监听-数据库查询的数据：{}", vehicle1);
    }
}
