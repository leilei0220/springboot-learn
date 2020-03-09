package com.leilei.controller;

import com.leilei.entity.User;
import com.leilei.response.JsonReturn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author : leilei
 * @date : 21:57 2020/3/8
 * @desc :
 */
@Api(tags = "swagger测试 接口")
@RestController
public class SwagerTestController {
    /**
     * 无参数
     *
     * @return
     */
    @GetMapping("one")
    @ApiOperation(value = "无参数接口", notes = "GET请求无参数接口测试")
    public JsonReturn testOne() {
        return JsonReturn.buildSuccess(null, "success");
    }

    /**
     * 对象参数
     *
     * @param user
     * @return
     */
    @PostMapping("insertUser")
    @ApiOperation(value = "用户新增 对象参数", notes = "新增一个用户")
    public JsonReturn insertUser(User user) {
        return JsonReturn.buildSuccess(user, "success");
    }

    /**
     * 对象参数 json格式
     *
     * @param user
     * @return
     */
    @PutMapping("insertUserRequestBody")
    @ApiOperation(value = "用户新增 对象参数 json格式", notes = "新增一个用户")
    public JsonReturn insertUserRequestBody(@RequestBody User user) {
        return JsonReturn.buildSuccess(user, "success");
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "删除", notes = "删除，传一个参数")
    public JsonReturn delete(@ApiParam(name = "id", value = "删除对象的id", required = true, example = "1L") Long id) {
        return JsonReturn.buildSuccess(id);
    }

    @PutMapping ("findMore")
    @ApiOperation(value = "查询多个", notes = "查询，传多个参数")
    public JsonReturn findMore(
            @ApiParam(name = "id", value = "对象的id", example = "1L") Long id,
            @ApiParam(name = "username", value = "查询对象的名字", example = "小明") String username,
            @ApiParam(name = "nickname", value = "查询对象的昵称", example = "明明")String nickname) {
        return JsonReturn.buildSuccess(null);
    }
}
