package com.leilei.controller;

import cn.hutool.core.bean.BeanUtil;
import com.leilei.entity.form.UserAddForm;
import com.leilei.entity.form.UserModifyForm;
import com.leilei.entity.po.User;
import com.leilei.entity.vo.ResultVO;
import com.leilei.entity.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author lei
 * @version 1.0
 * @date 2021/1/24 17:04
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "新增用户", notes = "新添加一个用户信息")
    @PostMapping
    @ResponseBody
    public ResultVO<UserVO> addUser(@RequestBody UserAddForm userAddForm) {
        User user = BeanUtil.copyProperties(userAddForm, User.class);
        user.setId(new Random().nextLong());
        user.setVersion(new Random().nextInt(10));
        return ResultVO.success(BeanUtil.copyProperties(user,UserVO.class));
    }
    @ApiOperation(value = "修改用户", notes = "修改用户信息")
    @PutMapping
    @ResponseBody
    public ResultVO<UserVO> modifyUser(@RequestBody UserModifyForm userAddForm) {
        return ResultVO.success(BeanUtil.copyProperties(userAddForm,UserVO.class));
    }

    @ApiOperation(value = "获取一条用户信息", notes = "获取一条用户信息")
    @GetMapping("/{id}")
    @ResponseBody
    public ResultVO<UserVO> getUser(@PathVariable("id") Long  id) {
        User user = new User();
        user.setId(id);
        user.setName("zs");
        user.setNickName("zs");
        user.setEmail("zs@qq.com");
        user.setVersion(1);
        return ResultVO.success(BeanUtil.copyProperties(user,UserVO.class));
    }

    @ApiOperation(value = "禁用用户信息", notes = "禁用用户信息")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultVO<Boolean> deleteUser(@PathVariable("id") Long  id) {
        return ResultVO.success(true);
    }

}
