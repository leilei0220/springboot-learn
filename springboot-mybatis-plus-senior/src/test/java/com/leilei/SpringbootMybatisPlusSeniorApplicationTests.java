package com.leilei;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.entity.Student;
import com.leilei.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisPlusSeniorApplicationTests {
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void testAR() {
        Student student = new Student();
        student.setId(2L);
        student.setName("必须把版本号传回去");
        student.setVersion(1);
        studentMapper.updateById(student);
    }

    @Test
    void testLogicDelete() {
        studentMapper.deleteById(2);
    }

    @Test
    void test() {
        studentMapper.selectById(2);
    }

    @Test
    void testInsert() {
        Student student = new Student();
        student.setName("小李");
        student.setAge(54);
        student.setCreateId(777L);
        student.insert();
    }

    @Test
    void testUpdate() {
        Student student = studentMapper.selectById(4);
        student.setName("雷电法王 杨永信+叶良辰");
        student.updateById();
    }



}
