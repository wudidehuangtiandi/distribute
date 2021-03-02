package com.controller;

import com.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


/**
 * @author god
 * @date 2020年 01月09日 10:02:40
 */
@Controller
public class TestController {

    @PostMapping("/login")
    public String login(String username, HttpSession session){
        session.setAttribute(Constant.USER_NAME,username);
        return "chat";
    }
}
