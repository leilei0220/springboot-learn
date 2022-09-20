package com.leilei.common;

import com.alibaba.fastjson.JSONObject;
import com.leilei.mysql.OperatorTypeEnum;
import io.debezium.data.Envelope;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author lei
 * @create 2022-09-20 13:53
 * @desc 读取数据操作工具类
 **/
@Log4j2
public class ReadInfoUtil {

    private ReadInfoUtil() {
    }

    public static final String DB = "db";
    public static final String TABLE = "table";
    public static final String TS_MS = "ts_ms";
    public static final String BIN_FILE = "file";
    public static final String POS = "pos";
    public static final String CREATE = "CREATE";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    public static final String SOURCE = "source";
    public static final String UPDATE = "UPDATE";
    public static Map<DataBaseEnum, ThreeConsumer<SourceRecord, Struct, DataChangeInfo>> databaseFillMap = new HashMap<>(2);

    /**
     * 使用策略模式 进行数据填充,减少IF判断胶水代码
     *
     * @author lei
     * @date 2022-09-20 14:35:12
     */
    static {
        databaseFillMap.put(DataBaseEnum.MYSQL, (sourceRecord, source, dataChangeInfo) -> {
            String topic = sourceRecord.topic();
            String[] fields = topic.split("\\.");
            String database = fields[1];
            String tableName = fields[2];
            dataChangeInfo.setFileName(Optional.ofNullable(source.get(BIN_FILE)).map(Object::toString).orElse(""));
            dataChangeInfo.setFilePos(Optional.ofNullable(source.get(POS)).map(x -> Integer.parseInt(x.toString())).orElse(0));
            dataChangeInfo.setDatabase(database);
            dataChangeInfo.setTableName(tableName);
        });

        databaseFillMap.put(DataBaseEnum.SQL_SERVER, (sourceRecord, source, dataChangeInfo) -> {
            dataChangeInfo.setDatabase(Optional.ofNullable(source.get(DB)).map(Object::toString).orElse(null));
            dataChangeInfo.setTableName(Optional.ofNullable(source.get(TABLE)).map(Object::toString).orElse(null));
        });
        log.info("首次加载数据库填充转换映射");
    }


    /**
     * 构建获取变更对象
     *
     * @param sourceRecord 原始数据
     * @param dataBaseEnum 数据库类型
     * @return DataChangeInfo
     * @author lei
     * @date 2022-09-20 14:47:50
     */
    public static DataChangeInfo getDataChangeInfo(SourceRecord sourceRecord, DataBaseEnum dataBaseEnum) {
        Struct struct = (Struct) sourceRecord.value();
        final Struct source = struct.getStruct(SOURCE);
        DataChangeInfo dataChangeInfo = new DataChangeInfo();
        // 获取操作类型  CREATE UPDATE DELETE
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);
        String type = operation.toString().toUpperCase();
        int eventType = type.equals(CREATE) ? OperatorTypeEnum.INSERT.getType() : UPDATE.equals(type) ?
                OperatorTypeEnum.UPDATE.getType() : OperatorTypeEnum.DELETE.getType();
        // fixme 一般情况是无需关心其之前之后数据的,直接获取最新的日志数据即可,但这里为了演示,都进行输出
        dataChangeInfo.setBeforeData(ReadInfoUtil.getJsonObject(struct, BEFORE).toJSONString());
        dataChangeInfo.setAfterData(ReadInfoUtil.getJsonObject(struct, AFTER).toJSONString());
        if (eventType == OperatorTypeEnum.DELETE.getType()) {
            dataChangeInfo.setData(ReadInfoUtil.getJsonObject(struct, BEFORE).toJSONString());
        } else {
            dataChangeInfo.setData(ReadInfoUtil.getJsonObject(struct, AFTER).toJSONString());
        }
        dataChangeInfo.setOperatorType(eventType);
        // 策略模式 根据数据库填充数据
        Optional.ofNullable(databaseFillMap.get(dataBaseEnum)).ifPresent(fillMap -> fillMap.accept(sourceRecord, source, dataChangeInfo));
        dataChangeInfo.setOperatorTime(Optional.ofNullable(struct.get(TS_MS)).map(x -> Long.parseLong(x.toString())).orElseGet(System::currentTimeMillis));
        return dataChangeInfo;
    }

    /**
     * 从袁术数据获取出变更之前或之后的数据
     *
     * @param value
     * @param fieldElement
     * @return JSONObject
     * @author lei
     * @date 2022-08-25 14:48:13
     */
    public static JSONObject getJsonObject(Struct value, String fieldElement) {
        Struct element = value.getStruct(fieldElement);
        JSONObject jsonObject = new JSONObject();
        if (element != null) {
            Schema afterSchema = element.schema();
            List<Field> fieldList = afterSchema.fields();
            for (Field field : fieldList) {
                Object afterValue = element.get(field);
                jsonObject.put(field.name(), afterValue);
            }
        }
        return jsonObject;
    }
}
