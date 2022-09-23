package com.leilei;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/5/30 19:48
 * @desc
 */
@Service
public class BinLogListener implements ApplicationRunner {
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
    public void run(ApplicationArguments args) {
        CanalConnector conn = getConn();
        conn.connect();
        conn.subscribe();
        try {
            while (true) {
                // 获取指定数量的数据
                Message message = conn.getWithoutAck(1000);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    Thread.sleep(1000);
                    continue;
                } else {
                    printEntry(message.getEntries());
                }
                // 提交确认
                conn.ack(batchId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理失败, 回滚数据
            conn.rollback();
        } finally {
            conn.disconnect();
        }

    }

    protected void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            long executeTime = entry.getHeader().getExecuteTime();
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN ||
                    entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            if (entry.getEntryType() == EntryType.ROWDATA) {
                CanalEntry.RowChange rowChange;
                try {
                    rowChange = RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }
                CanalEntry.EventType eventType = rowChange.getEventType();
                if (eventType == EventType.QUERY || rowChange.getIsDdl()) {
                    continue;
                }
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    if (eventType == EventType.DELETE) {
                        printColumn(rowData.getBeforeColumnsList());
                    } else if (eventType == CanalEntry.EventType.INSERT) {
                        printColumn(rowData.getAfterColumnsList());
                    } else {
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }

    /**
     * 获取连接
     */
    public CanalConnector getConn() {
        return CanalConnectors.newSingleConnector(new InetSocketAddress(host, port), instance, username, password);
    }

    protected void printColumn(List<Column> columns) {
        for (Column column : columns) {
            StringBuilder builder = new StringBuilder();
            try {
                if (StringUtils.containsIgnoreCase(column.getMysqlType(), "BLOB")
                        || StringUtils.containsIgnoreCase(column.getMysqlType(), "BINARY")) {
                    // get value bytes
                    builder.append(column.getName()).append(" : ").append(new String(column.getValue().getBytes("ISO-8859-1"), "UTF-8"));
                } else {
                    builder.append(column.getName()).append(" : ").append(column.getValue());
                }
            } catch (UnsupportedEncodingException e) {
            }
            builder.append("    type=").append(column.getMysqlType());
            if (column.getUpdated()) {
                builder.append("    update=").append(column.getUpdated());
            }
        }
    }
}
