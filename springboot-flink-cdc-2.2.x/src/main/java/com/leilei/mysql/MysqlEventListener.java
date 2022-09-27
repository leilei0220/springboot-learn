package com.leilei.mysql;


import com.leilei.common.DataChangeInfo;
import com.leilei.common.DataChangeSink;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * @author lei
 * @create 2022-09-20 11:15
 * @desc mysql变更监听 2.2.1版本
 **/
@Component
public class MysqlEventListener implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            MySqlSource<DataChangeInfo> mySqlSource = buildDataChangeSource();
            DataStream<DataChangeInfo> streamSource = env
                    .fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "mysql-source")
                    .setParallelism(1);
            streamSource.addSink(dataChangeSink);
            try {
                env.execute("mysql-stream-cdc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    /**
     * 构造变更数据源
     *
     * @param
     * @return DebeziumSourceFunction<DataChangeInfo>
     * @author lei
     * @date 2022-08-25 15:29:38
     */
    private MySqlSource<DataChangeInfo> buildDataChangeSource() {
        return MySqlSource.<DataChangeInfo>builder()
                .hostname("10.50.40.145")
                .port(3306)
                .databaseList("paas_common_db")
                // 支持正则匹配
                .tableList("^(paas_common_db.base_business_driver_score).*")
                // .tableList("paas_common_db.base_business_driver_score")
                .username("root")
                .password("cdwk-3g-145")

                /**initial初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 * 注：点击查看源码发现目前只支持initial以及latest了
                 */
                .startupOptions(StartupOptions.latest())
                .deserializer(new MysqlDeserialization())
                .serverTimeZone("GMT+8")
                .build();
    }
}
