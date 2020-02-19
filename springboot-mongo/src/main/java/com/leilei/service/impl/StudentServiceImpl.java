package com.leilei.service.impl;


import com.leilei.entity.Student;
import com.leilei.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : leilei
 * @date : 10:29 2020/2/16
 * @desc :
 */
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int insertStudent(Student student) {
        student.setTimer(LocalDateTime.now());
        mongoTemplate.insert(student);
        try {
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateStudent(Student student) {
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Update update = new Update().set("username", student.getUsername());
        try {
            mongoTemplate.updateFirst(query, update, Student.class);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int removeStudent(Long id) {
        Query query=new Query(Criteria.where("_id").is(id));
        try {
            mongoTemplate.remove(query,Student.class);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }


    }

    @Override
    public Student findOne(Student student) {
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Student one = mongoTemplate.findOne(query, Student.class);
        return one;
    }

    @Override
    public List<Student> findlike(Student student) {
        Pattern pattern = Pattern.compile("^.*" + student.getUsername().trim() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("username").regex(pattern));
        List<Student> studentList = mongoTemplate.find(query, Student.class);
        return studentList;
    }

    @Override
    public List<Student> findmore(Student student) {
        Query query = new Query(Criteria.where("username").is(student.getUsername()));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }

    @Override
    public List<Student> findtime(Student student) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timer"));
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }

    @Override
    public List<Student> findtimeByPage(Student student) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timer"));
        query.skip(0).limit(3);
        List<Student> students = mongoTemplate.find(query, Student.class);
        return students;
    }
}
