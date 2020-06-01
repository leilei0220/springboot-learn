package com.leilei.controller;

import com.leilei.entity.AuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lei
 * @version 1.0
 * @date 2020/5/31 20:30
 */
@Controller
public class ShiroController {
    /**
     * 跳首页
     *
     * @return
     */
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 跳登录页
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public String Login() {
        return "login";
    }

    /**
     * 无权限页
     *
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    /**
     * 登录方法
     *
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password,Boolean rememberMe, Model model) {

        Subject currentSubject = SecurityUtils.getSubject();
        if (rememberMe != null) {
            rememberMe = true;
        }else {
            rememberMe = false;
        }
        try {
            //判断当前用户是否登录 布尔值  取反
            if (!currentSubject.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
                currentSubject.login(token);
                return "index";
            }
            //已登录 则回到首页
            AuthUser authUser = (AuthUser) currentSubject.getPrincipal();
            return "index";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "账户不存在");
            return "login";
        }
    }

    /**
     * 角色首页
     *
     * @return
     */
    @RequestMapping("/user")
    public String user() {
        return "/user/index";
    }

    /**
     * 添加页
     *
     * @return
     */
    @RequestMapping("/user/add")
    public String userAdd() {
        return "/user/add";
    }

    /**
     * 修改页
     *
     * @return
     */
    @RequestMapping("/user/edit")
    public String userEdit() {
        return "/user/edit";
    }
}
