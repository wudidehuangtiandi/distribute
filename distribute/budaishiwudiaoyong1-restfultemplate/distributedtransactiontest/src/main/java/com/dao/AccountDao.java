package com.dao;

import com.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao {
    @Select("select*from account")
    public List<Account> findAll();

   // @Insert("insert into account(account,name)values(#{account},#{name}) ")
    /*第三个参数枚举为，直接操作，预处理，存储过程*/
    //@SelectKey(before=false,keyProperty="id",resultType=Integer.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    @Update("update account set account=#{account}+account where name =#{name}")
    public int saveAccount(Account account);
}
