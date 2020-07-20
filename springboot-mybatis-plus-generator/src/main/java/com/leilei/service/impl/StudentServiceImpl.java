package com.leilei.service.impl;

import com.leilei.entity.Student;
import com.leilei.mapper.StudentMapper;
import com.leilei.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author leilei
 * @since 2020-07-20
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
