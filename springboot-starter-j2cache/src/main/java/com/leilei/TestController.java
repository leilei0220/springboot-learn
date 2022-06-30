package com.leilei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2022-05-10 18:27
 * @desc
 **/
@RequestMapping("/test")
@RestController
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("/get/{num}")
    public TestBean getNum(@PathVariable("num")Integer num) {
        return testService.testBean(num);
    }

    @RequestMapping("/get/template")
    public Object getTemplate() {
        return testService.testMyTemplate();
    }

    @RequestMapping("/evict")
    public void evictNum() {
         testService.evict();
    }
    @RequestMapping("/evict/{id}")
    public void evictNum(@PathVariable("id") Integer id) {
         testService.evict(id);
    }
}
