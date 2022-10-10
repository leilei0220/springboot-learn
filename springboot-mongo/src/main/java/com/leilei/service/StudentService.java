package com.leilei.service;


import com.leilei.entity.PageVO;
import com.leilei.entity.Student;
import com.leilei.entity.StudentQueryForm;
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
public class StudentService {
    private final MongoTemplate mongoTemplate;

    public StudentService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public int insertStudent(Student student) {
        student.setTimer(LocalDateTime.now());
        mongoTemplate.insert(student);
        return 1;

    }


    public int updateStudent(Student student) {
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Update update = new Update().set("username", student.getUsername());
        mongoTemplate.updateFirst(query, update, Student.class);
        return 1;
    }


    public int removeStudent(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Student.class);
        return 1;
    }


    public Student findOne(Student student) {
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        return mongoTemplate.findOne(query, Student.class);
    }


    public List<Student> findLike(Student student) {
        Pattern pattern = Pattern.compile("^.*" + student.getUsername().trim() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("username").regex(pattern));
        return mongoTemplate.find(query, Student.class);
    }


    public List<Student> findMore(Student student) {
        Query query = new Query(Criteria.where("username").is(student.getUsername()));
        return mongoTemplate.find(query, Student.class);
    }


    public List<Student> findTime(Student student) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "timer"));
        return mongoTemplate.find(query, Student.class);
    }

    /**
     * mongodb分页查询需要自己拼装一下
     *
     * @param queryForm
     * @return PageVO<Student>
     * @author lei
     * @date 2022-08-26 14:53:43
     */
    public PageVO<Student> findByPage(StudentQueryForm queryForm) {

        Query query = new Query();
        if (queryForm.getUsername() != null) {
            query.addCriteria(Criteria.where("username").is(queryForm.getUsername()));
        }
        // 1.根据条件查询总数 总数无数据则反回空数据分页对象
        int count = (int) mongoTemplate.count(query, Student.class);
        if (count < 1) {
            return PageVO.emptyResult();
        }
        query.with(new Sort(Sort.Direction.DESC, "timer"));
        // 2.有数据,则查询指定页数数据 (skip =(当前页-1)*指定页长度)
        int skip = (queryForm.getPageIndex() - 1) * queryForm.getPageSize();
        query.skip(skip).limit(queryForm.getPageSize());
        List<Student> students = mongoTemplate.find(query, Student.class);
        // 3.获取总页数 总数除每页长度
        // 4.拼接返回分页响应
        return PageVO.getPageResult(students, queryForm.getPageIndex(), queryForm.getPageSize(), count);
    }
}
