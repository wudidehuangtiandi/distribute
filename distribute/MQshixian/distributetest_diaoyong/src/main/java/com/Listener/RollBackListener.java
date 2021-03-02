package com.Listener;

import com.dao.AccountDao;
import com.domain.Account;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 失败回滚监听
 *
 * @author GC
 * @date 2020年 02月02日 16:31:17
 */
@Component
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)/*没调TestServiceImpl所以加个事务。*/
public class RollBackListener {

    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Autowired
    private AccountDao accountDao;

    @RabbitListener(queues = "MQFalseTranQueue")
    public void handleMessage(@Payload Account account, Channel channel, @Headers Map<String, Object> headers) throws Exception {

        String spring_returned_message_correlation = headers.get("spring_returned_message_correlation").toString();

        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        Object o = stringRedisTemplate.opsForValue().get(spring_returned_message_correlation);
        if (o!=null){
            //消费过直接返回即可
            channel.basicAck(tag, false);
        }else {
            //没消费过,保持一天
            stringRedisTemplate.opsForValue().set(spring_returned_message_correlation,"true",1L, TimeUnit.DAYS);
            //这边万一回滚失败还是要重试几次的，但是最终难以保证完全一致性。
            int i = accountDao.RollBack(account);
            channel.basicAck(tag, false);
        }

    }
}
