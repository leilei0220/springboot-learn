package com.leilei.controller;

import com.leilei.entity.three.Student;
import com.leilei.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : leilei
 * @date : 16:43 2020/2/16
 * @desc :
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping("add")
    public String add(Student student) {
        int i = studentService.insertStudent(student);
        if (i==i) {
            return "success";
        }
        return "false";
    }
}
