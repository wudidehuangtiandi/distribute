package com.service;

import com.domain.Account;

import java.util.List;

public interface AccountService {
    public List<Account> fingAll();

    public int saveAccount(Account account);
}
