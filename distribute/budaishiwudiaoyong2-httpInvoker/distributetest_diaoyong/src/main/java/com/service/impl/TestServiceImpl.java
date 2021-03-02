package com.service.impl;

import com.dao.AccountDao;
import com.domain.Account;


import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.stereotype.Service;



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
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;


    public String getMethod() {
        Account account = new Account();
        account.setName("zhang");
        account.setaccount(50);
        accountService.saveAccount(account);



        return null;

    }

}
