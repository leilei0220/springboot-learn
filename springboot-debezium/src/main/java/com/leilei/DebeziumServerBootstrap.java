package com.leilei;

import io.debezium.engine.DebeziumEngine;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author lei
 * @version 1.0
 * @date 2021/6/14 14:58
 * @desc 官网配置 sql server 详细示例 https://debezium.io/documentation/reference/1.5/connectors/sqlserver.html
 * @desc 官网配置 mysql 详细示例https://debezium.io/documentation/reference/1.5/connectors/mysql.html
 */
@Data
public class DebeziumServerBootstrap implements InitializingBean, SmartLifecycle {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private DebeziumEngine<?> debeziumEngine;

    @Override
    public void start() {
        executor.execute(debeziumEngine);
    }

    @SneakyThrows
    @Override
    public void stop() {
        debeziumEngine.close();
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(debeziumEngine, "DebeZiumEngine must not be null");
    }
}
