package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 14:58
 * @desc
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    /**
     *  Cacheable：
     *  缓存
     *  如果缓存没有，则会执行方法并加入缓存中，如果缓存中有则直接返回缓存中数据
     *  value 可理解为缓存的库 （存在哪个位置）
     *  key  缓存库中的找某数据的唯一键 （通过缓存库+键即可找到对应缓存）
     * @param id
     * @return
     */
    @Cacheable(value = "userCache", key = "#id")
    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }
    /**
     *  Cacheable：
     *  缓存
     *  如果缓存没有，则会执行方法并加入缓存中，如果缓存中有则直接返回缓存中数据
     *  value 可理解为缓存的库 （存在哪个位置）
     *  key  缓存库中的找某数据的唯一键 （通过缓存库+键即可找到对应缓存）
     * @param userId
     * @return
     */
    @Cacheable(value = "userCacheByCreateId", key = "#userId")
    public List<User> findAll(String userId) {
        return userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getCreateId, userId));
    }

    /**
     * CachePut：更新缓存 每次都会进入到方法体中
     *  value 可理解为缓存的库 （存在哪个位置）
     *  key 缓存库中的找某数据的唯一键
     * 修改数据，更新对应缓存 （一二级缓存都会更改）
     * @param user
     * @return
     */
    @CachePut(value = "userCache", key = "#user.id")
    public User modifyUser(User user) {
        userMapper.updateById(user);
        return user;
    }

    /**
     * CacheEvict ：清除缓存
     *  value = {"userCacheByCreateId"},key = "#user.createId" ：指定清除一二级缓存中指定缓存库与缓存键的缓存
     *
     * @param user
     * @return
     */
    @CacheEvict(value = {"userCacheByCreateId"},allEntries = true )
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

    /**
     * CacheEvict ：清除缓存
     * 未指定Key  且allEntries=true 此操作会同时清除一级缓存（内存）与二级缓存Redis指定库中所有数据
     * @param id
     * @return
     */
    @CacheEvict(value = {"userCache","userCacheByCreateId"},allEntries = true)
    public Boolean deleteById(Integer id) {
        int i = userMapper.deleteById(id);
        return i > 1;
    }
}
