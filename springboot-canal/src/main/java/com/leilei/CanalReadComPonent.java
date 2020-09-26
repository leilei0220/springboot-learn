//package com.leilei;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.client.CanalConnectors;
//import com.alibaba.otter.canal.protocol.CanalEntry;
//import com.alibaba.otter.canal.protocol.Message;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.InetSocketAddress;
//import java.util.List;
//import java.util.UUID;
//
//
///**
// * @description: 定时读取binlog日志
// * @author: lei
// * @create: 2020-09-26 21:06
// **/
////@Component
//public class CanalReadComPonent implements ApplicationRunner {
//
//    @Value("${canal.host}")
//    private String host;
//    @Value("${canal.port}")
//    private int port;
//    @Value("${canal.username}")
//    private String username;
//    @Value("${canal.password}")
//    private String password;
//    @Value("${canal.instance}")
//    private String instance;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        CanalConnector conn = getConn();
//        while (true){
//            conn.connect();
//            //订阅实例中所有的数据库和表
//            conn.subscribe();
//            // 回滚到未进行ack的地方
//            conn.rollback();
//            // 获取数据 每次获取一百条改变数据
//            Message message = conn.getWithoutAck(1000000);
//
//            long id = message.getId();
//            int size = message.getEntries().size();
//            if (id != -1 && size >0){
//                // 数据解析
//                analysis(message.getEntries());
//            }
//            // 确认消息
//            conn.ack(message.getId());
//            // 关闭连接
//            conn.disconnect();
//        }
//    }
//
//    /**
//     * 数据解析
//     */
//    private void analysis(List<CanalEntry.Entry> entries) {
//        for (CanalEntry.Entry entry : entries) {
//            // 只解析mysql事务的操作，其他的不解析
//            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN ) {
//                continue;
//            }
//            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND){
//                continue;
//            }
//
//            // 解析binlog
//            CanalEntry.RowChange rowChange = null;
//
//            try {
//                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // 获取操作类型字段（增加  删除  修改）
//            CanalEntry.EventType eventType = rowChange.getEventType();
//            // 获取当前操作所属的数据库
//            String dbName = entry.getHeader().getSchemaName();
//            // 获取当前操作所属的表
//            String tableName = entry.getHeader().getTableName();
//            // 事务提交时间
//            Long timestamp = entry.getHeader().getExecuteTime();
//            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
//                dataDetails(rowData, dbName, tableName, eventType,timestamp);
//                System.out.println("-------------------------------------------------------------");
//            }
//        }
//    }
//
//
//
//    /**
//     * 解析具体一条Binlog消息的数据
//     * @param dbName        当前操作所属数据库名称
//     * @param tableName     当前操作所属表名称
//     * @param eventType     当前操作类型（新增、修改、删除）
//     */
//    private static void dataDetails(CanalEntry.RowData rowData,
//                                    String dbName,
//                                    String tableName,
//                                    CanalEntry.EventType eventType,
//                                    long timestamp) {
//        if (CanalEntry.EventType.QUERY.equals(eventType)){
//            return;
//        }
//        System.out.println("数据库："+ dbName);
//        System.out.println("表名："+ tableName);
//        System.out.println("操作类型:"+ eventType);
//        System.out.println("操作时间:"+ timestamp);
//        if (CanalEntry.EventType.INSERT.equals(eventType)){
//            System.out.println("新增数据："+rowData.getAfterColumnsList());
//        }else if(CanalEntry.EventType.DELETE.equals(eventType)){
//            System.out.println("删除数据："+rowData.getBeforeColumnsList());
//        }else {
//
//            System.out.println("-------修改前");
//            printColumn(rowData.getBeforeColumnsList());
//            System.out.println("-------修改后");
//            printColumn(rowData.getAfterColumnsList());
//        }
//
//    }
//
//
//    private static void printColumn(List<CanalEntry.Column> columns) {
//        for (CanalEntry.Column column : columns) {
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
//        }
//    }
//
//    /**
//     * 获取连接
//     */
//    public CanalConnector getConn() {
//        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(host, port), instance, username, password);
//        return canalConnector;
//    }
//
//
//}
