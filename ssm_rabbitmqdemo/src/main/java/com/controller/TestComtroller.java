package com.controller;

import com.pojo.ParamReq;
import com.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 作为\生产者
 *
 * @author GC
 * @date 2020年 01月30日 12:35:40
 */
@Controller
public class TestComtroller {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/direct", method = { RequestMethod.GET })
    public String direct() {
        ParamReq paramReq = new ParamReq();
        paramReq.setContent("收到了吗？我发送了");
        paramReq.setMessageType("topic");
        testService.sendMessage(paramReq);
        return "success";
    }

}
