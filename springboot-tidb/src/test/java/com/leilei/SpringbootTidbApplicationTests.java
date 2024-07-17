package com.leilei;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTidbApplicationTests {

    @Autowired
    private StudentMapper studentMapper;


    @Test
    public void testInsert() {
        Student student = new Student();
        student.setName("leilei");
        student.setAge(18);
        studentMapper.insert(student);
        System.out.println(student.getId());
        System.out.println(studentMapper.selectById(student.getId()));
    }

    @Test
    public void testUpdate() {
        Student student = new Student();
        student.setId(804315137);
        student.setName("leilei-update");
        student.setAge(18);
        studentMapper.updateById(student);
        System.out.println(studentMapper.selectById(student.getId()));
    }

    @Test
    public void testDelete() {
        studentMapper.deleteById(1);
        studentMapper.deleteById(1857138690);
    }

    @Test
    public void testQuery() {
        System.out.println(studentMapper.selectById(1));
        System.out.println(studentMapper.selectById(1857138690));
        System.out.println(studentMapper.selectById(804315137));
        System.out.println(studentMapper.selectById(-1393553406));
    }

    @Test
    public void testPageQuery() {
        System.out.println(studentMapper.selectPage(new Page<>(1, 10), null));
        System.out.println("--------");
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<Student>().eq(Student::getName, "leilei");
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(1, 10), wrapper);
        System.out.println(studentPage);
    }
}
