## 数据库

### 启动Docker Mysql容器并设置binlog

```shell
# 运行mysql容器 
docker run --name mysql-service -v /var/lib/mysql/data:/var/lib/mysql -v /var/lib/mysql/conf:/etc/mysql/conf.d -p 3306:3306 -e TZ=Asia/Shanghai -e MYSQL_ROOT_PASSWORD=leiMysql.. -d mysql:5.7.30 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-time_zone="+8:00"
# 设置binlog位置
docker exec mysql-service bash -c "echo 'log-bin=/var/lib/mysql/mysql-bin' >> /etc/mysql/mysql.conf.d/mysqld.cnf"
# 配置 mysql的server-id
docker exec mysql-service bash -c "echo 'server-id=123' >> /etc/mysql/mysql.conf.d/mysqld.cnf"

```
### 启动Docker Ms-sql容器并开启代理以及CDC
```shell
Docker 安装SQL SERVER
docker pull microsoft/mssql-server-linux:2017-latest 
# 安装
# 说明
#-e ACCEPT_EULA = Y 设置ACCEPT_EULA变量为任何值，以确认你接受最终用户许可协议。 SQL Server 映像的必需设置。

#-e MSSQL_SA_PASSWORD =<YourStrong ！Passw0rd> 指定你自己的强密码至少 8 个字符并达到SQL Server 密码要求。 SQL Server 映像的必需设置。
docker run -d -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=123456aA!' -p 1433:1433 --name sqlserver2017 -v /var/lib/mssql_data:/opt/mssql_data microsoft/mssql-server-linux:2017-latest

# 进入内部 开启代理  
docker exec -it sqlserver2017 "bash"
# 内部执行以下命令
/opt/mssql/bin/mssql-conf set sqlagent.enabled true
# 退出容器
exit
# 重启容器
docker restart sqlserver2017

#------以下操作在Navicat中也可执行
# 查看指定SQL SERVER数据库是否开启CDC功能
SELECT is_cdc_enabled,CASE WHEN is_cdc_enabled=0 THEN 'CDC功能禁用' ELSE 'CDC功能启用' END 描述
FROM sys.databases
WHERE NAME = 'GPS'

开启CDC功能
# 使用指定库
use gps
# 执行命令
EXECUTE sys.sp_cdc_enable_db

#查看当前已经开启CDC的数据表
SELECT name,is_tracked_by_cdc FROM sys.tables WHERE is_tracked_by_cdc = 1;
开启表CDC
示例：

对'USRALMHS'表开启变更捕获

EXEC sys.sp_cdc_enable_table
@source_schema= 'dbo',      --源表架构
@source_name = 'USRALMHS',  --源表
@role_name = 'CDC_Role'     --角色（将自动创建）
GO

--如果不想控制访问角色，则@role_name必须显式设置为null。
```

### 创建表

```sql
  create schema etl;

create table user_info
(
    user_id     varchar(64)          not null
        primary key,
    username    varchar(100)         null comment '用户名',
    age         int(3)               null comment '年龄',
    gender      tinyint(1)           null comment '字典类型',
    remark      varchar(255)         null comment '描述',
    create_time datetime             null comment '创建时间',
    create_id   varchar(64)          null comment '创建人ID',
    update_time datetime             null comment '修改时间',
    update_id   varchar(64)          null comment '修改人ID',
    enabled     tinyint(1) default 1 null comment '删除状态（1-正常，0-删除）'
)
    comment '字典表';
```
## 配置

`DebeziumConfiguration` 中的一些路径配置需要设置成你自己的。

## 启动 

启动本项目，你可以采用各种手段往数据库增删改数据，观察会有类似下面的打印：

```shell
payload = {user_id=i, username=zs, age=11 , gender=0, enabled=1}
```
