package com.leilei.mysql;


import com.leilei.common.DataBaseEnum;
import com.leilei.common.DataChangeInfo;
import com.leilei.common.ReadInfoUtil;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * @author lei
 * @create 2022-08-25 13:43
 * @desc mysql消息读取自定义序列化
 **/
public class MysqlDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {

    /**
     * 反序列化数据,转为变更JSON对象
     *
     * @param sourceRecord
     * @param collector
     * @return void
     * @author lei
     * @date 2022-08-25 14:44:31
     */
    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DataChangeInfo> collector) {
        DataChangeInfo dataChangeInfo = ReadInfoUtil.getDataChangeInfo(sourceRecord, DataBaseEnum.MYSQL);
        // 输出数据
        collector.collect(dataChangeInfo);
    }


    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }
}
