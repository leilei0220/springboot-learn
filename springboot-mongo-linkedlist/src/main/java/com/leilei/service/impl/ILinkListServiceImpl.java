package com.leilei.service.impl;

import com.leilei.entity.City;
import com.leilei.entity.School;
import com.leilei.entity.Student;
import com.leilei.entity.StudentClass;
import com.leilei.service.ILinkListService;
import com.leilei.util.response.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : leilei
 * @date : 11:30 2020/3/15
 * @desc :
 */
@Service
public class ILinkListServiceImpl implements ILinkListService {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加测试数据
     *
     * @return
     */
    @Override
    public JsonReturn addData() {
        List<Student> students = Arrays.asList(
                new Student(1L, "小明", 1L),
                new Student(2L, "小红", 2L),
                new Student(3L, "小菜", 2L));


        List<StudentClass> studentClasses = Arrays.asList(
                new StudentClass(1L, "三年级一班", 1L),
                new StudentClass(2L, "三年级二班", 2L));


        List<School> schools = Arrays.asList(
                new School(1L, "旺仔小学", 1L),
                new School(2L, "蒙牛小学", 1L));

        City city = new City();
        city.setId(1L);
        city.setCityName("希望市");

        try {
            mongoTemplate.insertAll(students);
            mongoTemplate.insertAll(studentClasses);
            mongoTemplate.insertAll(schools);
            mongoTemplate.save(city);
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("student", students);
            map.put("studentClass", studentClasses);
            map.put("schools", schools);
            map.put("city", city);
            return JsonReturn.buildSuccess(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("error");

        }
    }

    /**
     * 多对一查询
     *
     * @param studentId
     * @param classId
     * @return
     */
    @Override
    public JsonReturn MoreToOne(Long studentId, Long classId) {
        LookupOperation lookup = LookupOperation.newLookup()
                //关联的从表名字
                .from("studentClass")
                //主表中什么字段与从表相关联
                .localField("classId")
                //从表中的什么字段与主表相关联
                .foreignField("_id")
                //自定义的从表结果集名  与主表关联的数据归于此结果集下
                .as("class");
        Criteria criteria = new Criteria();
        if (studentId != null) {
            //主表可能选择的条件
            criteria.and("_id").is(studentId);
        }
        //从表可能选择的条件
        if (classId != null) {
            //class 为我之前定义的从表结果集名
            criteria.and("class._id").is(classId);
        }
        //将筛选条件放入管道中
        MatchOperation match = Aggregation.match(criteria);
        Aggregation agg = Aggregation.newAggregation(lookup, match,Aggregation.unwind("class"));
        try {
            AggregationResults<Map> studentAggregation = mongoTemplate.aggregate(agg, "student", Map.class);
            return JsonReturn.buildSuccess(studentAggregation.getMappedResults());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("error");
        }
    }

    /**
     *一对多
     *
     * @return
     */
    @Override
    public JsonReturn oneToMany() {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                //关联的表
                .from("student")
                //主表以什么字段与从表相关联的
                .localField("_id")
                //从表关联的字段
                .foreignField("classId")
                //定义的从表数据查询出的结果结合
                .as("studentList");
        Aggregation agg = Aggregation.newAggregation(lookupOperation);
        try {
            AggregationResults<Map> studentAggregation = mongoTemplate.aggregate(agg, "studentClass", Map.class);
            return JsonReturn.buildSuccess(studentAggregation.getMappedResults());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("error");
        }
    }

    /**
     * 多表一对一  以Student为主表（第一视角）
     *
     * @return
     */
    @Override
    public JsonReturn moreTableOneToOne() {
        //学生关联班级
        LookupOperation lookupOne = LookupOperation.newLookup()
                //关联的从表  （班级)
                .from("studentClass")
                //主表中什么字段与从表（班级)关联
                .localField("classId")
                //从表（班级）什么字段与主表关联字段对应
                .foreignField("_id")
                //从表结果集
                .as("class");

        //班级关联学校  那么此时 这两者之间 班级又是 学校的主表 班级还是学生的从表
        LookupOperation lookupTwo = LookupOperation.newLookup()
                //班级关联的从表（学校)
                .from("school")
                //主表中什么字段与从表（学校）关联  因为班级也是student从表  且已经设了结果集为class  那么主表字段也只能结果集.字段
                .localField("class.schoolId")
                .foreignField("_id")
                .as("school");

        //学校关联城市 两者之前 学校则为城市二者关联关系中的主表  学校还是班级的从表
        LookupOperation lookupThree = LookupOperation.newLookup()
                //学校关联的从表（城市)
                .from("city")
                //学校是班级的从表 且设了结果集名为school 那么要获取学校字段 也只能由之前设立的学校结果集名.字段 来获取了
                .localField("school.cityId")
                .foreignField("_id")
                .as("city");
        //将几者关联关系放入管道中 作为条件进行查询
        Aggregation aggregation = Aggregation.newAggregation(lookupOne, lookupTwo, lookupThree);
        try {
            //注意，我这里还是以student为主表  那么查询结果第一视角（最外层）还是为student
            AggregationResults<Map> aggregate = mongoTemplate.aggregate(aggregation, "student", Map.class);
            return JsonReturn.buildSuccess(aggregate.getMappedResults());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("error");
        }

    }
}
