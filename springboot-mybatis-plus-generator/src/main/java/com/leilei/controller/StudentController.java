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
 * @author leilei
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    public IStudentService studentService;

    /**
     * POST请求方式
     *
     * @param student 保存的对象
     * @return JsonReturn
     */
    @PostMapping
    public JsonReturn insert(Student student) {
        try {
            student.setVersion(0);
            return JsonReturn.buildSuccess(studentService.save(student) ? student :
            "student--新增失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }

    }

    /**
     * PUT请求方式
     *
     * @param  student 要修改的对象
     * @return JsonReturn
     */
    @PutMapping
    public JsonReturn update(Student student) {
        try {
            student.setVersion(studentService.getById(student.getId()).getVersion());
            return JsonReturn.buildSuccess(studentService.updateById(student) ? student:
            "student--修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }

    }

    /**
     * 批量删除 DELETE请求方式
     *
     * @param ids Long 类型 List 集合
     * @return JsonReturn
     */
    @DeleteMapping("remove")
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
     * 查询一个 GET请求
     *
     * @param id 查找对象的主键ID
     * @return JsonReturn
     */
    @GetMapping("findOne/{id}")
    public JsonReturn findOne(@PathVariable("id") Long id) {
        try {
            Student student =studentService.getById(id);
            return JsonReturn.buildSuccess(student, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }
    }

    /**
     * 查询所有 GET
     *
     * @param pageNum  当前页
     * @param pageSize 每页最大数据数
     * @return JsonReturn
     */
    @GetMapping("findAll/{pageNum}/{pageSize}")
    public JsonReturn findAll(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        try {
            Page<Student> page = studentService.page(new Page<Student>(pageNum, pageSize));
            return JsonReturn.buildSuccess(page, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturn.buildFailure("操作失败" + e.getMessage());
        }
    }

}
