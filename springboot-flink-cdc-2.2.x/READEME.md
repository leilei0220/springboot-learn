注意事项：
 
注意flink 与cdc-connector 版本对应关系,cdc-connector与对应数据库驱动关系
 
ex:
 
flink 1.13.x | mysql cdc:2.2.1 | mysql-driver：8.0.26以下 
 
8.0.26 会报错：tried to access field com.mysql.cj.CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME from class io.debezium.connector.mysql.antlr.MySqlAntlrDdlParser

从 8.0.26 开始，MySQL 驱动程序更改了以下变量访问语义，com.mysql.cj.CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME，此变量从公共改为私有