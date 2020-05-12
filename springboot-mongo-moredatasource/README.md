# springboot-mongo-moredatasource
SpringBoot整合MongoDB（二）多数据源配置，

Aggregation管道使用 （类似于MYsql的集合函数 ，分组啊，统计等等）

decument 为我三个库所用的脚本文件  

使用打开navicat 连接自己的mongo数据库后 选择库点击 运行脚本文件   选中脚本 即可导入数据


####2020/05/11补充 

关于单副本集连接使用 以及多数据源下 事务处理

目前 一个方法中 只写入某一数据源下 数据  回滚没有问题 ，但是在一个方法中 同时向多个数据源写入的话
仍只会回滚 teanslation 指定的事务管理器下 数据，，，，

###多数据源下 统一事务管理 待完成......
