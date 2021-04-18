package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.entity.User;
import com.leilei.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${zs:true}")
    private Boolean bb;
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
     * CachePut： 每次都会进入到方法中（并更新缓存）
     *  value 可理解为缓存的库 （存在哪个位置）
     *   key 缓存库中的找某数据的唯一键
     * 修改数据，更新对应缓存
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
     *  value = {"userCacheByCreateId"},key = "#user.createId" ：指定清除库中某键缓存
     *  也可清空指定库中所有缓存  value = {"userCacheByCreateId"},allEntries = true
     * @param user
     * @return
     */
    @CacheEvict(value = {"userCacheByCreateId"},key = "#user.createId")
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

}
