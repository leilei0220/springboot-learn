package com.leilei.controller;


import com.leilei.entity.PageVO;
import com.leilei.entity.Student;
import com.leilei.entity.StudentQueryForm;
import com.leilei.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

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

    @PostMapping("findOne")
    public Student findOne(Student student) {
        return studentService.findOne(student);

    }

    @PostMapping("findLike")
    public List<Student> findLike(Student student) {
        return studentService.findLike(student);

    }

    @PostMapping("findMore")
    public List<Student> findMore(Student student) {
        return studentService.findMore(student);

    }
    @PostMapping("findTime")
    public List<Student> findTime(Student student) {
        return studentService.findTime(student);

    }
    @PostMapping("findByPage")
    public PageVO<Student> findByPage(@RequestBody StudentQueryForm queryForm) {
        return studentService.findByPage(queryForm);

    }
}
