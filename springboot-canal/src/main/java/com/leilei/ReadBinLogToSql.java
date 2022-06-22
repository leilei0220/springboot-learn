package com.leilei;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author lei
 * @version 1.0
 * @date 2020/9/27 22:33
 * @desc 读取binlog日志
 */
@Component
public class ReadBinLogToSql implements ApplicationRunner {
    //读取的binlog sql 队列缓存 一边Push 一边poll
    private Queue<String> canalQueue = new ConcurrentLinkedQueue<>();
    @Value("${canal.host}")
    private String host;
    @Value("${canal.port}")
    private int port;
    @Value("${canal.username}")
    private String username;
    @Value("${canal.password}")
    private String password;
    @Value("${canal.instance}")
    private String instance;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CanalConnector conn = getConn();
        conn.connect();
        //订阅实例中所有的数据库和表
        conn.subscribe(".*\\\\..*");
        // 回滚到未进行ack的地方
        conn.rollback();
        try {
            while (true) {
                // 获取数据 每次获取一百条改变数据
                Message message = conn.getWithoutAck(100);
                long id = message.getId();
                int size = message.getEntries().size();
                if (id != -1 && size > 0) {
                    // 数据解析
                    analysis(message.getEntries());
                    // 确认消息
                    conn.ack(message.getId());
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            conn.disconnect();
        }
    }

    private void analysis(List<Entry> entries) throws InvalidProtocolBufferException {
        for (Entry entry : entries) {
            if (EntryType.ROWDATA == entry.getEntryType()) {
                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
                EventType eventType = rowChange.getEventType();
                if (eventType == EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == EventType.INSERT) {
                    saveInsertSql(entry);
                }
            }
        }
    }

    /**
     * 保存更新语句
     *
     * @param entry
     */
    private void saveUpdateSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> dataList = rowChange.getRowDatasList();
            for (RowData rowData : dataList) {
                List<Column> afterColumnsList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("update " +
                        entry.getHeader().getTableName() + " set ");
                for (int i = 0; i < afterColumnsList.size(); i++) {
                    sql.append(" ")
                            .append(afterColumnsList.get(i).getName())
                            .append(" = '").append(afterColumnsList.get(i).getValue())
                            .append("'");
                    if (i != afterColumnsList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" where ");
                List<Column> oldColumnList = rowData.getBeforeColumnsList();
                for (Column column : oldColumnList) {
                    if (column.getIsKey()) {
                        sql.append(column.getName()).append("=").append(column.getValue());
                        break;
                    }
                }
                canalQueue.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存删除语句
     *
     * @param entry
     */
    private void saveDeleteSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> columnList = rowData.getBeforeColumnsList();
                StringBuffer sql = new StringBuffer("delete from " +
                        entry.getHeader().getTableName() + " where ");
                for (Column column : columnList) {
                    if (column.getIsKey()) {
                        sql.append(column.getName()).append("=").append(column.getValue());
                        break;
                    }
                }
                canalQueue.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存插入语句
     *
     * @param entry
     */
    private void saveInsertSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> datasList = rowChange.getRowDatasList();
            for (RowData rowData : datasList) {
                List<Column> columnList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("insert into " +
                        entry.getHeader().getTableName() + " (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append(columnList.get(i).getName());
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(") VALUES (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append("'" + columnList.get(i).getValue() + "'");
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                canalQueue.add(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     */
    public CanalConnector getConn() {
        return CanalConnectors.newSingleConnector(new InetSocketAddress(host, port), instance, username, password);
    }


    /**
     * 模拟消费canal转换的sql语句
     */
    public void executeQueueSql() {
        int size = canalQueue.size();
        for (int i = 0; i < size; i++) {
            String sql = canalQueue.poll();
            System.out.println("canal 监听到主库sql变化----> " + sql);
        }
    }

}
