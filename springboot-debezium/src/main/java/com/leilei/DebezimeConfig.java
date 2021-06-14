package com.leilei;

import io.debezium.connector.sqlserver.SqlServerConnector;
import io.debezium.data.Envelope;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static io.debezium.data.Envelope.FieldName.*;
import static java.util.stream.Collectors.toMap;

/**
 * @author lei
 * @version 1.0
 * @date 2021/6/14 15:11
 * @desc 官网配置 sql server 详细示例 https://debezium.io/documentation/reference/1.5/connectors/sqlserver.html
 * @desc 官网配置 mysql 详细示例https://debezium.io/documentation/reference/1.5/connectors/mysql.html
 */
@Configuration
public class DebezimeConfig {

//    @Bean
//    io.debezium.config.Configuration debeziumConfig() {
//        return io.debezium.config.Configuration.create()
////            连接器的Java类名称
//                .with("connector.class", MySqlConnector.class.getName())
////            偏移量持久化，用来容错 默认值
//                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
////                偏移量持久化文件路径 默认/tmp/offsets.dat  如果路径配置不正确可能导致无法存储偏移量 可能会导致重复消费变更
////                如果连接器重新启动，它将使用最后记录的偏移量来知道它应该恢复读取源信息中的哪个位置。
//                .with("offset.storage.file.filename", "E://spring-boot-debezium/tmp/offsets.dat")
////                捕获偏移量的周期
//                .with("offset.flush.interval.ms", "6000")
////               连接器的唯一名称
//                .with("name", "mysql-connector")
////                数据库的hostname
//                .with("database.hostname", "xx")
////                端口
//                .with("database.port", "3306")
////                用户名
//                .with("database.user", "root")
////                密码
//                .with("database.password", "xx..")
////                 包含的数据库列表
//                .with("database.include.list", "etl")
////                是否包含数据库表结构层面的变更，建议使用默认值true
//                .with("include.schema.changes", "false")
////                mysql.cnf 配置的 server-id
//                .with("database.server.id", "123")
////                	MySQL 服务器或集群的逻辑名称
//                .with("database.server.name", "customer-mysql-db-server")
////                历史变更记录
//                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
////                历史变更记录存储位置，存储DDL
//                .with("database.history.file.filename", "E://spring-boot-debezium/tmp/dbhistory.dat")
//                .build();
//    }

    @Bean
    io.debezium.config.Configuration debeziumConfig() {
        return io.debezium.config.Configuration.create()
//            连接器的Java类名称
                .with("connector.class", SqlServerConnector.class.getName())
//            偏移量持久化，用来容错 默认值
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
//                偏移量持久化文件路径 默认/tmp/offsets.dat  如果路径配置不正确可能导致无法存储偏移量 可能会导致重复消费变更
//                如果连接器重新启动，它将使用最后记录的偏移量来知道它应该恢复读取源信息中的哪个位置。
                .with("offset.storage.file.filename", "E://springboot-debezium/tmp/offsets.dat")
//                捕获偏移量的周期
                .with("offset.flush.interval.ms", "6000")
//               连接器的唯一名称
                .with("name", "mssql-connector")
//                数据库的hostname
                .with("database.hostname", "xx")
//                端口
                .with("database.port", "1433")
//                用户名
                .with("database.user", "sa")
//                密码
                .with("database.password", "xx")
//                 包含的数据库列表
                .with("database.dbname", "lei_test_cdc")
//                是否包含数据库表结构层面的变更，建议使用默认值true
                .with("include.schema.changes", "false")
                .with("table.include.list", "dbo.student,dbo.people")
//                	MySQL 服务器或集群的逻辑名称
                .with("database.server.name", "customer-db-sql-server")
//                历史变更记录
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
//                历史变更记录存储位置，存储DDL
                .with("database.history.file.filename", "E://springboot-debezium/tmp/dbhistory.dat")
                .build();
    }

    /**
     * Debezium server bootstrap debezium server bootstrap.
     *
     * @param configuration the configuration
     * @return the debezium server bootstrap
     */
    @Bean
    DebeziumServerBootstrap debeziumServerBootstrap(io.debezium.config.Configuration configuration) {
        DebeziumServerBootstrap debeziumServerBootstrap = new DebeziumServerBootstrap();
        DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine = DebeziumEngine
                .create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(this::handlePayload)
                .build();
        debeziumServerBootstrap.setDebeziumEngine(debeziumEngine);
        return debeziumServerBootstrap;
    }


    private void handlePayload(List<RecordChangeEvent<SourceRecord>> recordChangeEvents,
                               DebeziumEngine.RecordCommitter<RecordChangeEvent<SourceRecord>> recordCommitter) {
        recordChangeEvents.forEach(r -> {
            SourceRecord sourceRecord = r.record();
            Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
            if (sourceRecordChangeValue != null) {
                // 判断操作的类型 过滤掉读 只处理增删改   这个其实可以在配置中设置
                Envelope.Operation operation = Envelope.Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
                if (operation != Envelope.Operation.READ) {
                    String record = operation == Envelope.Operation.DELETE ? BEFORE : AFTER;
                    // 获取增删改对应的结构体数据
                    Struct struct = (Struct) sourceRecordChangeValue.get(record);
                    // 将变更的行封装为Map
                    Map<String, Object> payload = struct.schema().fields().stream()
                            .map(Field::name)
                            .filter(fieldName -> struct.get(fieldName) != null)
                            .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                            .collect(toMap(Pair::getKey, Pair::getValue));
                    // 这里简单打印一下
                    System.out.println("payload = " + payload);
                }
            }
        });
    }
}
