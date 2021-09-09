# springboot-cache-redis

使用spring cache相关注解，将数据缓存到redis中
spring cache相关注解如下
@Cacheable（获取、添加缓存），
@CachePut（修改缓存）， 但是一般不用 缓存操作避免脏数据 最好的方式就是 创建,有变动直接删除,非热点数据不查不缓存。
@CacheEvict（删除缓存）

我们可以自定义注解 添加修改删除数据的时候，一次删除多个相关的缓存库 
