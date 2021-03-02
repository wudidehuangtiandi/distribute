package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * shiro增删改查
 *
 * @author GC
 * @date 2020年 01月15日 19:39:28
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    //登陆方法
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Subject currentUser = SecurityUtils.getSubject();
        //如果没登陆
        if (!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //记住我，配置了rememberMeCookie这边只要开出来即可使用，实际使用时这边要传个checkbox的值，如果被选中再将记住我开出来
            //token.setRememberMe(true);
            //登陆
            try {
                currentUser.login(token);//传到realm处理
            } catch (AuthenticationException e) {//所有认证异常的父类异常
                //e.printStackTrace();这种打印，不好，占用字符串常量池可能会导致项目挂掉
                //正确应该是输入到日志里
                System.out.println("登陆失败"+e.getMessage());//这和第一种不同，第一种是打印整个轨迹
                throw new ArithmeticException("登陆失败");
            }
        }
        return "welcome";
    }


}
