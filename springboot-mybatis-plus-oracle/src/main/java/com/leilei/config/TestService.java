package com.leilei.config;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author: lei
 * @date: 2024-11-13 15:27
 * @desc:
 */
@Service
public class TestService {

    private final StudentMapper studentMapper;

    public TestService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }


    @PostConstruct
    public void init() {
        Student student = new Student();
        student.setUsername("aaa");
        studentMapper.insert(student);
    }
}
