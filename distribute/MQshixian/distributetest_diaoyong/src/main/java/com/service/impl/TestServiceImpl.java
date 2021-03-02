package com.service.impl;

import com.Listener.ConfirmCallBackLinster;
import com.dao.AccountDao;
import com.domain.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式调用测试
 *
 * @author GC
 * @date 2020年 01月31日 14:22:15
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TestServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    @Qualifier("amqpDirectTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmCallBackLinster confirmCallBackLinster;

    @Autowired
    private RedisTemplate stringRedisTemplate;


    //采用spring的restTemplate来调用另一个项目的添加50方法
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getMethod() {

        String url = "http://localhost:8080/account/saveAll";
        // 设置请求的 Content-Type 为 application/json
        MultiValueMap<String, String> header = new LinkedMultiValueMap();
        //header.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 设置 Accept 向服务器表明客户端可处理的内容类型
        header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 直接将实体 Account 作为请求参数传入，底层利用 Jackson 框架序列化成 JSON 串发送请求
        // HttpEntity和@RequestBody和@ResponseBody很像。出了能够访问请求和响应体，HttpEntity（和子类ResponseEntity）也能访问请求（和响应）头
        Account account = new Account();
        account.setName("zhang");
        account.setaccount(50);
        MultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("name", account.getName());
        map.add("account", account.getaccount());
        //HttpEntity<Account> request = new HttpEntity<Account>(account, header);
        HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<>(map, header);
        ResponseEntity<String> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return null;

    }


    /*先调用隔壁的加50，再调用自己的减10*/
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String getMethod2() {

        Account account = new Account();
        account.setName("zhang");
        account.setaccount(50);


        //操作自己的方法来减去10
        int i = 0;
        try {
            i = accountDao.saveAccount(account);
        } catch (Exception e) {

           return "failed";
        }



     /*   String url = "http://localhost:8080/account/saveAll";
        // 设置请求的 Content-Type 为 application/json
        MultiValueMap<String, String> header = new LinkedMultiValueMap();
        //header.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        header.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 设置 Accept 向服务器表明客户端可处理的内容类型
        header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 直接将实体 Account 作为请求参数传入，底层利用 Jackson 框架序列化成 JSON 串发送请求
        // HttpEntity和@RequestBody和@ResponseBody很像。出了能够访问请求和响应体，HttpEntity（和子类ResponseEntity）也能访问请求（和响应）头
        Account account = new Account();
        account.setName("zhang");
        account.setaccount(50);
        MultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("name",account.getName());
        map.add("account",account.getaccount());
        //HttpEntity<Account> request = new HttpEntity<Account>(account, header);
        HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<>(map, header);
        ResponseEntity<String> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);*/



        if(i>0){
            //设置消息唯一标识，生产上可以准确定位消息,生产上不用截取或者可以加上别的保证唯一性
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().substring(0, 8));
            //存到redis方便重试和消费端幂等(实际这边还要考虑redis主从配置防止redis挂掉)
            ObjectMapper om = new ObjectMapper();
            try {
                String s = om.writeValueAsString(account);
                //唯一标识及存活时间
                stringRedisTemplate.opsForValue().set(correlationData.getId(),s,600L, TimeUnit.SECONDS);
                //到交换机的重试次数
                stringRedisTemplate.opsForValue().set("T"+correlationData.getId(),"0",600L,TimeUnit.SECONDS);


                //rabbitTemplate.convertAndSend("employee.*",消息体或者对象, new MessagePostProcessor() {}

                rabbitTemplate.convertAndSend(account, new MessagePostProcessor() {
                            @Override
                            public Message postProcessMessage(Message message) throws AmqpException {
                                message.getMessageProperties().getHeaders().put("type", "人员ID");
                                return message;
                            }
                        }, correlationData
                );

                return "send success";


            } catch (JsonProcessingException e) {
                //这边e应该打印到日志
                //中断程序
                throw new RuntimeException();
            }

        }else return "failed";



    }


}
