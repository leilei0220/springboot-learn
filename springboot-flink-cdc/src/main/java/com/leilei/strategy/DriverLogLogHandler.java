package com.leilei.strategy;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author lei
 * @create 2022-08-29 14:15
 * @desc 驾驶员对应处理器
 **/
@Service
@Log4j2
public class DriverLogLogHandler implements BaseLogHandler<DriverPO>{


    @Override
    public void handleInsertLog(DriverPO data, Long operatorTime) {
        log.info("处理驾驶员新增日志：{}", data);
    }

    @Override
    public void handleUpdateLog(DriverPO data, Long operatorTime) {
        log.info("处理驾驶员修改日志：{}", data);
    }

    @Override
    public void handleDeleteLog(DriverPO data, Long operatorTime) {
        log.info("处理驾驶员删除日志：{}", data);
    }
}
