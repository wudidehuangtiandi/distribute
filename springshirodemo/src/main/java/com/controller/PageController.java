package com.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * shiro页面跳转
 *
 * @author GC
 * @date 2020年 01月14日 13:58:05
 */
@Controller
@RequestMapping("page")
public class PageController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/success")
    public String loginsuccess(){
        return "welcome";
    }
    @RequestMapping("/unauthor")
    public String unauthor(){
        return "unauthor";
    }
    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("/user")
    public String user(){
        return "user";
    }

    //权限注解测试
    @RequestMapping("/test")
    @RequiresRoles({"admin"})//不要放service层，当出现事务控制时同时用会有问题，代理的代理不可取
    public String test(){
        return "test";
    }
}
