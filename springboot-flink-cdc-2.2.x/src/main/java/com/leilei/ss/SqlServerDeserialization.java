package com.leilei.ss;

import com.leilei.common.DataBaseEnum;
import com.leilei.common.DataChangeInfo;
import com.leilei.common.ReadInfoUtil;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * @author lei
 * @create 2022-09-20 11:41
 * @desc sqlServer数据变更反序列化解析器
 **/
public class SqlServerDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {


    @Override
    public void deserialize(SourceRecord record, Collector<DataChangeInfo> out) {
        DataChangeInfo dataChangeInfo = ReadInfoUtil.getDataChangeInfo(record, DataBaseEnum.SQL_SERVER);
        out.collect(dataChangeInfo);
    }

    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }
}
