package com.service.impl;

import com.dao.AccountDao;
import com.domain.Account;
import com.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    public List<Account> fingAll() {
        List<Account> account = accountDao.findAll();
        return account;
    }

    public void saveAccount(Account account) {
        int s = accountDao.saveAccount(account);
        System.out.println(account);
    }
}
