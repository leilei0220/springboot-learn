package com.leilei;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2023-05-10 10:26
 * @desc
 **/
@Api(tags = "测试分组")
@RequestMapping("test")
@RestController
public class TestController {

    @ApiOperation("测试自定义方法")
    @GetMapping("xx")
    public String testXX() {
        return "ok";
    }
}
