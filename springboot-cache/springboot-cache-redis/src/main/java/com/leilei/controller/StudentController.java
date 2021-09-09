package com.leilei.controller;

import com.leilei.entity.Student;
import com.leilei.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2021/4/18 15:00
 * @desc
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 根据ID 查询信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    private Student findStudentById(@PathVariable("id") Integer id) {
        return studentService.findStudentById(id);
    }

    /**
     * 新增信息
     * @param student
     * @return
     */
    @PostMapping
    private Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    /**
     * 删除
     * @param student
     * @return
     */
    @PutMapping
    private Student modifyStudent(@RequestBody Student student) {
        return studentService.modifyStudent(student);
    }

    /**
     * 模拟根据用户创建者查询所有
     * @return
     */
    @GetMapping("/byCreateId/{createId}")
    private List<Student> findAllByCreateId(@PathVariable("createId") String createId) {
        return studentService.findAllByCreateId(createId);
    }

}
