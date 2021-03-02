package com.listener;

import com.pojo.ParamReq;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听者（消费者）注意：不同模式要修改队列名哦
 *
 * @author GC
 * @date 2020年 01月30日 12:44:44
 */
@Component
public class TestListener {
/*从哪个队列接收*/
    @RabbitListener(queues = "${rabbit.common.queue}" )
    public void handleMessage(ParamReq paramReq) {
        /*得到信息对象后打印*/
        System.out.println("1"+paramReq);
    }

    @RabbitListener(queues = "${rabbit.common.queue2}")
    public void handleMessage2(ParamReq paramReq) {
        /*得到信息对象后打印*/
        System.out.println("2"+paramReq);
    }
}