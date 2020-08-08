package com.leilei.controller;

import com.leilei.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/8/7 23:50
 * @desc
 */
@RestController
public class StudentController {
    @GetMapping("/test")
    public List<Student> test() {
        Student student = new Student();
        return student.selectList(null);
    }
}
