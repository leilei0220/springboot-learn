##springboot-redis-cluster

Springboot整合Redis Cluster
  
String  set  hash list zSet  存储结构的使用

结合 fastjson 的使用

redis事务只支持单机，不支持cluster

需要开启事务时，只需要在对应的方法或类上使用@Transactional注解即可，SpringBoot自动开启了@EnableTransactionManagement

需要注意事务不生效的几种情况

redis事务依赖于jdbc的事务管理
