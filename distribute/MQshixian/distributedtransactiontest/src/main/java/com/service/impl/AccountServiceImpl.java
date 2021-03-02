package com.service.impl;

import com.dao.AccountDao;
import com.domain.Account;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;




    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public List<Account> fingAll() {
        List<Account> account = accountDao.findAll();
        return account;
    }
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int saveAccount(Account account) {
        int s = accountDao.saveAccount(account);
        int a =1/0;//出现异常回滚两个服务的事务
        System.out.println(account);
        return s;

    }
}
