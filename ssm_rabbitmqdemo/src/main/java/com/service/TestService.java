package com.service;

import com.pojo.ParamReq;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 生产者\业务层 不同模式要修改业务层注入的模板哦
 *
 * @author GC
 * @date 2020年 01月30日 12:42:18
 */
@Service
public class TestService {

    @Autowired
    @Qualifier("amqpTopicTemplate")
    AmqpTemplate amqpTemplate;

    public void sendMessage(ParamReq paramReq){
        amqpTemplate.convertAndSend(paramReq);
    }
}
