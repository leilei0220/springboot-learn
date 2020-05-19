# springboot-learn
leilei -springboot学习小营地

springboot-easypoi  整合easypoi 导入导出 以及 导入数据校验

springboot-email   整合邮件（可自行选择邮件客户端）普通文本 html  附件 静态资源 四种类型

springboot-fastdfs 整合 fdfs文件存储服务器（单机） 文件上传下载 fdfs安装在Linux 虚拟机中

springboot-file-qiniu springboot 中 整合七牛云 文件存储 

springboot-file-upload-download springboot 中对文件进行上传下载 删除 （文件存放在本地 未使用fastdfs 或者七牛云）

springboot-https springboot 项目同时支持 http 和 https 两种协议 并解决 https协议中 post请求变变为get的问题

springboot-interceptor  自定义注解实现登录拦截与放行场景 并获取登录用户信息 自定义NotNeedLogin注解 进行接口放行

springboot-jsr303 使用 jsr303 进行参数校验规则 并使用 RestControllerAdvice增强 做统一异常处理 将异常信息以自定义json格式进行返回

springboot-logback 日志管理

springboot-mongo 整合Mongodb  单数据源 CRUD 基础操作

springboot-mongo-linkedlist 整合mongodb 进阶 单数据源 连表查询 

springboot-mongo-moredatasource 整合Mongodb 再次进阶 高级查询与操作  多数据源 mongo库 配置密码后不同连接方式 AggregationOperation 管道的使用

springboot-mybatis 整合Mybatis  基础使用 crud 连表 Druid数据源监控 以及代码生成器

springboot-mybatis-atomikos-moredatasource mysql多数据源 整合 Mybatis Druid多数据源监控 跨库连表查询 以及事务管理

springboot-mybatis-plus 整合mybatis-plus mybatis-plus 基本使用 Druid数据源监控 代码生成器 模板定义

springboot-mybatis-plus-atomikos 多数据源下 Druid多数据源监控 以及mybatisplus 中使用XXXapper.xml 

springboot-profile springboot下多环境配置 打包一 使用yml 选择打包配置文件

springboot-profile-two springboot下多环境配置 打包二 使用mvn 命令打包对应配置文件 灵活打包 不用进入项目修改Yml   exmaple:打包命令 idea 进入项目目录下 使用Terminal终端   mvn clean install -P test  此命令就会打包test环境

springboot-qrcode 整合 zxing 生成二维码 直接生成有无img 二维码 或者 二维码 base64编码

springboot-read-yml springboot 读取配置文件 自定义配置文件 以及POM.xml 文件内容

springboot-redis-cluster 整合 redis单机版 cluster 集群 五种存储类型 （String，set，zset,hash,list)使用 以及单机版事务测试

springboot-sms 整合阿里云短信发送工具

springboot-swagger 整合swagger 接口文档工具
