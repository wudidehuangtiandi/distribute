package com.listener;

import com.domain.Account;

import com.rabbitmq.client.Channel;
import com.service.impl.AccountServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MQ调用的监听类
 *
 * @author GC
 * @date 2020年 02月01日 12:27:43
 */
@Component
public class Listerner {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private RedisTemplate stringRedisTemplate;
    @Autowired
    @Qualifier("amqpDirectTemplatefailed")
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "MQTranQueue")
    public void handleMessage(@Payload Account account, Channel channel, @Headers Map<String, Object> headers) throws Exception {


        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        if (headers.get("type") != null) {
            System.out.println("---------------");
            String type = headers.get("type").toString();
            System.out.println("头消息是:" + type);
            System.out.println("---------------");
        }
        String spring_returned_message_correlation = null;
        if (headers.get("spring_returned_message_correlation") != null) {
            spring_returned_message_correlation = headers.get("spring_returned_message_correlation").toString();
        }
        try {
            /*将唯一标识以k，v结构写入redis保证幂等*/
            Object k1 = stringRedisTemplate.boundValueOps(spring_returned_message_correlation).get();
            if (k1 == null) {
                //redis不存在则消费,并且写入,周期为一天（看情况更改）
                int i = accountService.saveAccount(account);
                stringRedisTemplate.opsForValue().set(spring_returned_message_correlation, "true", 1L, TimeUnit.DAYS);
            } else {
                //消息已经消费过释放即可
                channel.basicNack(tag, false, true);
                return;
            }

            stringRedisTemplate.delete("C" + spring_returned_message_correlation);
            channel.basicAck(tag, false);

        } catch (Exception e) {
            Object k1 = stringRedisTemplate.boundValueOps("C" + spring_returned_message_correlation).get();
            if (k1 == null) {
                stringRedisTemplate.opsForValue().set("C" + spring_returned_message_correlation, "0");
            } else {
                String s = k1.toString();
                int i = Integer.parseInt(s);
                i += 1;
                String s1 = Integer.toString(i);
                stringRedisTemplate.opsForValue().set("C" + spring_returned_message_correlation, s1);
            }

            //如果有唯一标识继续排队接受处理，没有或者重试次数达到上限则通知生产者回滚并打印日志
            if (StringUtils.isNotEmpty("C" + spring_returned_message_correlation) && Integer.parseInt(stringRedisTemplate.boundValueOps("C" + spring_returned_message_correlation).get().toString()) < 3) {
                channel.basicNack(tag, false, true);

            } else {
                stringRedisTemplate.delete("C" + spring_returned_message_correlation);
                //进入失败的回调队列//理论上应该和生产者一样搞个回调重试这边就省略了。
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().substring(0, 8));
                rabbitTemplate.convertAndSend(account, new MessagePostProcessor(){
                            @Override
                            public Message postProcessMessage(Message message) throws AmqpException {
                                message.getMessageProperties().getHeaders().put("type", "人员ID");
                                return message;
                            }
                        }, correlationData
                );
                channel.basicAck(tag, false);

            }

        }
        //Nack第二个参数手动提交，第三个参数true表示重新排队，false表示不排队（消息被消费）
        //channel.basicNack(tag,false,false);

    }
}
