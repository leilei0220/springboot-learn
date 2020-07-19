package com.leilei.controller;

import com.leilei.service.IStudentService;
import com.leilei.entity.Student;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leilei.util.response.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  student前端控制器  RestController注解 将结果以JSON形式返回
 * </p>
 *
 * @author ${leilei}
 * @since 2020-07-12
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    public IStudentService studentService;

    /**
     * 保存修改公用 POST请求方式
     * @param student 修改或保存的对象
     * @return JsonReturn
     */
    @PostMapping("/save")
    public JsonReturn save(Student student) {
        if (student.getId() != null){
            try {
                studentService.updateById(student);
                return JsonReturn.buildSuccess(student, "操作成功");
            } catch (Exception e) {
                e.printStackTrace();
                return JsonReturn.buildFailure("操作失败" + e.getMessage());
            }
        }
        try {
            studentService.save(student);
            return JsonReturn.buildSuccess(student, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }

    }

    /**批量删除 支持POST GET
     * @param ids Long 类型 List 集合
     * @return JsonReturn
     */
    @RequestMapping("remove")
    public JsonReturn delete(@RequestBody List<Long> ids) {
        try {
            studentService.removeByIds(ids);
            return JsonReturn.buildSuccess(ids, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }
    }

    /**
     * 查询一个  支持POST GET
     *
     * @param id 查找对象的主键ID
     * @return JsonReturn
     */
    @RequestMapping("findOne/{id}")
    public JsonReturn findOne(@PathVariable("id") Long id) {
        try {
            Student student = studentService.getById(id);
            return JsonReturn.buildSuccess(student, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }
    }


    /**查询所有 支持POST GET ,未传当前页以及分页长度 则默认1页 10条数据
     * @param pageNum 当前页
     * @param pageSize 每页最大数据数
     * @return JsonReturn
     */
    @RequestMapping("findAll")
    public JsonReturn findAll(Integer pageNum, Integer pageSize) {

        if (pageNum != null && pageSize != null) {
            try {
                Page<Student> page = studentService.page(new Page<Student>(pageNum, pageSize));
                return JsonReturn.buildSuccess(page, "操作成功");
            } catch (Exception e) {
                e.printStackTrace();
                return JsonReturn.buildFailure("操作失败" + e.getMessage());
            }
        }
        try {
            Page<Student> page = studentService.page(new Page<>());
            return JsonReturn.buildSuccess(page, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }
    }
}
