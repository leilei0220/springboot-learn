package com.leilei.controller;



import com.leilei.entity.Student;
import com.leilei.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : leilei
 * @date : 10:28 2020/2/16
 * @desc :
 */
@RestController
@RequestMapping("/lei")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping("add")
    public String add(Student student) {
        int i = studentService.insertStudent(student);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @PostMapping("update")
    public String update(Student student) {
        int i = studentService.updateStudent(student);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @GetMapping("remove")
    public String remove(Long id) {
        int i = studentService.removeStudent(id);
        if (i == 1) {
            return "success";
        }
        return "false";
    }

    @PostMapping("findone")
    public Student findone(Student student) {
        Student one = studentService.findOne(student);
        return one;

    }

    @PostMapping("findlike")
    public List<Student> findlike(Student student) {
        List<Student> findlike = studentService.findlike(student);
        return findlike;

    }

    @PostMapping("findmore")
    public List<Student> findmore(Student student) {
        List<Student> findlike = studentService.findmore(student);
        return findlike;

    }
    @PostMapping("findtime")
    public List<Student> findtime(Student student) {
        List<Student> findlike = studentService.findtime(student);
        return findlike;

    }
    @PostMapping("findtimeByPage")
    public List<Student> findtimeByPage(Student student) {
        List<Student> findlike = studentService.findtimeByPage(student);
        return findlike;

    }
}
