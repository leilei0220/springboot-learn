package com.leilei.ss;

import com.leilei.common.DataChangeInfo;
import com.leilei.common.DataChangeSink;
import com.ververica.cdc.connectors.sqlserver.SqlServerSource;
import com.ververica.cdc.connectors.sqlserver.table.StartupOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author lei
 * @create 2022-09-20 11:30
 * @desc sqlServer数据变更监听器
 **/
@Component
public class SqlServerEventListener implements ApplicationRunner {


    private final DataChangeSink dataChangeSink;

    public SqlServerEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            SourceFunction<DataChangeInfo> sourceFunction = SqlServerSource.<DataChangeInfo>builder()
                    .hostname("10.50.100.68")
                    .port(1433)
                    .database("GPS")
                    .tableList("dbo.GPSVehicleDriverBind")
                    .username("sa")
                    .startupOptions(StartupOptions.latest())
                    .password("8AL16x76NP3R")
                    .deserializer(new SqlServerDeserialization())
                    .build();
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.addSource(sourceFunction).filter(Objects::nonNull).addSink(dataChangeSink).setParallelism(1);
            try {
                env.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
}
