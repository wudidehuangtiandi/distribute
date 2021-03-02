package com.controller;

import com.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author GC
 * @date 2020年 01月31日 14:19:49
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @RequestMapping("/getmethod")
    public String getMethod(){
        String method = testService.getMethod();
        return "list";
    }
    @RequestMapping("/getmethod2")
    public String getMethod2(){
        String method = testService.getMethod2();
        return "list";
    }
}
