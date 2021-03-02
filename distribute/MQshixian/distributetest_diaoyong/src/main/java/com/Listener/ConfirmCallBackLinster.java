package com.Listener;

import com.dao.AccountDao;
import com.domain.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 在发送到队列过程中出现的问题的处理
 * @author GC
 * @date 2020年 02月01日 14:04:35
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)/*没调TestServiceImpl所以加个事务。*/
public class ConfirmCallBackLinster implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    @Autowired
    private RedisTemplate stringRedisTemplate;
    @Autowired
    @Qualifier("amqpDirectTemplate")
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AccountDao accountDao;

    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {


        //这一步是消息到交换机的过程
        //ack为true消息被成功发到交换机,false则是被退回
        String id = correlationData.getId();
        if (ack) {
            System.out.println("消息成功发送到交换机");
            System.out.println("消息标识是:" + id);
            //清除这项的次数
            stringRedisTemplate.delete(id);
        } else {
            System.out.println("消息被拒收的原因是:" + cause);
            /*这里可以判断下失败原因，某些原因可以不重试*/


            //反序列化
            ObjectMapper om = new ObjectMapper();
            //重试信息获得
            Object o = stringRedisTemplate.boundValueOps(id).get();
            String s = o.toString();
            Account accounts = null;
            try {
                accounts = om.readValue(s, Account.class);
            } catch (IOException e) {
                //打印日志回滚失败
            }
            try {


                /*重试次数限制*/
                Object o1 = stringRedisTemplate.boundValueOps("T" + id).get();
                int i = Integer.parseInt(o1.toString());
                if (i >= 2) {
                    System.out.println("重发次数达到上限");
                    //调用方法返回提示
                    //回滚生产者并清除redis相关内容
                    int i1 = accountDao.RollBack(accounts);
                    stringRedisTemplate.delete("T" + id);
                    return;
                }
                i += 1;
                String s1 = Integer.toString(i);
                stringRedisTemplate.opsForValue().set("T" + id, s1, 600, TimeUnit.SECONDS);

                //重发
                rabbitTemplate.convertAndSend(accounts, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().getHeaders().put("type", "人员ID");
                        return message;
                    }
                }, correlationData);

            } catch (Exception e) {
                //e要打印到日志，可能产生的问题：转换异常，序列化异常，超时等
                //调用方法返回提示
                //重试失败回滚生产者并清除redis相关内容
                int i1 = accountDao.RollBack(accounts);
                stringRedisTemplate.delete("T" + id);
                throw new RuntimeException();
            }
        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String desc, String exchangeName, String routingKey) {
        //交换机到队列的过程
        //消息没有找到合适的队列被退回
        System.out.println(desc);
        System.out.println("消息被退回");
        System.out.println("被退回的消息是 :" + new String(message.getBody()));
        System.out.println("被退回的消息编码是 :" + replyCode);

        //打印日志，如果message中有唯一标识也可以重试几次。
    }
}
