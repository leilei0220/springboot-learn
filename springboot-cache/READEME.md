# springboot-cache 内存缓存

@Cacheable（获取、添加缓存），
@CachePut（修改缓存），
@CacheEvict（删除缓存）

可以通过@Caching注解组合多个注解集合在一个方法上
ex:
```markdown
@Caching(cacheable = {
            @Cacheable(value = "userCache",key = "#user.id"),
            ...
    },
    put = {
            @CachePut(value = "userCache",key = "#user.id"),
            ...
    },evict = {
            @CacheEvict(value = "userCache",key = "#user.id"),
            ....
    })
    public User save(User user) {
        ....
    }
```