package com.leilei.service;

import com.leilei.config.AjaxResult;
import com.leilei.entity.three.Student;

/**
 * @author : leilei
 * @date : 16:16 2020/2/16
 * @desc :
 */
public interface IStudentService {
    AjaxResult insertStudent(Student student) throws Exception;
}
