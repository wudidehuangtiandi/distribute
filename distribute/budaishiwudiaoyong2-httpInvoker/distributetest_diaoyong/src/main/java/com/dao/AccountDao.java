package com.dao;

import com.domain.Account;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {

    @Update("update account set account=account-10 where name =#{name}")
    public void saveAccount(Account account);

}
