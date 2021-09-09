package com.leilei.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.contants.CacheAbleStudent;
import com.leilei.contants.CacheAbleStudentByCreateId;
import com.leilei.entity.Student;
import com.leilei.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 14:58
 * @desc
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentService {

    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentMapper studentMapper) {

        this.studentMapper = studentMapper;
    }

    /**
     *  Cacheable：
     *  缓存
     *  如果缓存没有，则会执行方法并加入缓存中，如果缓存中有则直接返回缓存中数据
     *  value 可理解为缓存的库 （存在哪个位置）
     *  key  缓存库中的找某数据的唯一键 （通过缓存库+键即可找到对应缓存）
     *
     *  unless = "#result == null " 结果不为空才存入redis
     * @param id
     * @return
     */
    //@Cacheable(value = "userCache", key = "#id",unless = "#result == null ")
    @CacheAbleStudent
    public Student findStudentById(Integer id) {
        return studentMapper.selectById(id);
    }

    /**
     * unless="#result == null || #result.size() == 0" 集合不为空是才存入redis
     * @param createId
     * @return
     */
    //@Cacheable(value = "userCacheByCreateId", key = "#createId",unless="#result == null || #result.size() == 0")
    @CacheAbleStudentByCreateId
    public List<Student> findAllByCreateId(String createId) {
        return studentMapper.selectList(new QueryWrapper<Student>().lambda().eq(Student::getCreateId, createId));
    }

    /**
     * CachePut： 每次都会进入到方法中（并更新缓存）
     *  value 可理解为缓存的库 （存在哪个位置）
     *   key 缓存库中的找某数据的唯一键
     * 修改数据，更新对应缓存
     * @param student
     * @return
     */
    //@CachePut(value = "userCache", key = "#student.id")
    //public Student modifyStudent(Student student) {
    //    studentMapper.updateById(student);
    //    return student;
    //}

    @CacheEvict(value = "userCache", key = "#student.id")
    public Student modifyStudent(Student student) {
        studentMapper.updateById(student);
        return student;
    }

    /**
     * CacheEvict ：清除缓存
     *  value = {"userCacheByCreateId"},key = "#user.createId" ：指定清除库中某键缓存
     *  也可清空指定库中所有缓存  value = {"userCacheByCreateId"},allEntries = true
     * @param student
     * @return
     */
    @CacheEvict(value = {"userCacheByCreateId"},key = "#student.createId")
    public Student createStudent(Student student) {
        studentMapper.insert(student);
        return student;
    }

}
