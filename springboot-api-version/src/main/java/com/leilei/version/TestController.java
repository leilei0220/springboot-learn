package com.leilei.version;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2022-11-02 22:24
 * @desc
 **/
@RequestMapping("/test")
@RestController
@ApiVersion(version = "v2")
public class TestController {

    @GetMapping("/demo")
    // @ApiVersion(version = "v1")
    public String demo() {
        return "demo";
    }
}
