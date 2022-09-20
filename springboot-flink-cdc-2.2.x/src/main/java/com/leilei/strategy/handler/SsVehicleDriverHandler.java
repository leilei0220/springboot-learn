package com.leilei.strategy.handler;

import com.leilei.strategy.entity.GpsVehicleDriverBind;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2022-09-20 14:42
 * @desc sqlServer 数据库的车辆驾驶员绑定关系处理器
 **/
@Service
@Log4j2
public class SsVehicleDriverHandler implements BaseLogHandler<GpsVehicleDriverBind> {

    @Override
    public void handleInsertLog(GpsVehicleDriverBind data, Long operatorTime) {
        log.info("处理sql-server 车辆驾驶员新增日志：{}", data);
    }

    @Override
    public void handleUpdateLog(GpsVehicleDriverBind data, Long operatorTime) {
        log.info("处理sql-server 车辆驾驶员修改日志：{}", data);
    }

    @Override
    public void handleDeleteLog(GpsVehicleDriverBind data, Long operatorTime) {
        log.info("处理sql-server 车辆驾驶员删除日志：{}", data);
    }
}
