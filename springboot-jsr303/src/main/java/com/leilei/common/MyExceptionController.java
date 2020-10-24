package com.leilei.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error"})
    public AjaxResult error(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        if (status == 404) {
            return AjaxResult.error("404啦！！", 404);
        }
           return AjaxResult.error("错误了", -1);
    }
}