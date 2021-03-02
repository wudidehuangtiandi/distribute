package com.service.impl;

import com.dao.AccountDao;
import com.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;

/**
 * 分布式调用测试
 *
 * @author GC
 * @date 2020年 01月31日 14:22:15
 */
@Service
public class TestServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountDao accountDao;


    //采用spring的restTemplate来调用另一个项目的添加50方法
    public String getMethod() {

        String url = "http://localhost:8080/account/saveAll";
        // 设置请求的 Content-Type 为 application/json
        MultiValueMap<String, String> header = new LinkedMultiValueMap();
        //header.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        header.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 设置 Accept 向服务器表明客户端可处理的内容类型
        header.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        // 直接将实体 Account 作为请求参数传入，底层利用 Jackson 框架序列化成 JSON 串发送请求
        // HttpEntity和@RequestBody和@ResponseBody很像。除了能够访问请求和响应体，HttpEntity（和子类ResponseEntity）也能访问请求（和响应）头
        Account account = new Account();
        account.setName("zhang");
        account.setaccount(50);
        MultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("name",account.getName());
        map.add("account",account.getaccount());
        //HttpEntity<Account> request = new HttpEntity<Account>(account, header);
        HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<>(map, header);
        ResponseEntity<String> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return null;

    }

    /*先调用隔壁的加50，再调用自己的减50*/
    public String getMethod2() {

        String url = "http://localhost:8080/account/saveAll";
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
        ResponseEntity<String> exchangeResult = restTemplate.exchange(url, HttpMethod.POST, request, String.class);


        //操作自己的方法来减去10
        accountDao.saveAccount(account);

        return null;


    }
}
