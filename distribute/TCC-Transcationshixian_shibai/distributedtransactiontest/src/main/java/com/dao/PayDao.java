package com.dao;

import com.domain.Order;
import com.domain.Pay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


public interface PayDao {

    //给张扣钱
    @Update("update account_users set account=account-#{money}")
    public int paymoney(BigDecimal money);

    //回滚张的钱
    @Update("update account_users set account=account+#{money}")
    public int rollbackmoney(BigDecimal money);

    //插入付款单
    @Insert("insert into account_pay (paynum,status,name) values(#{paynum},#{status},#{name})")
    @SelectKey(before=false,keyProperty="id",resultType=Integer.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    public int payList(Pay pay);

    //支付单状态查询
    @Select("select * from pay where id=#{payno}")
    public Pay findPayStatus(Order order);

    //确认付款单
    @Update("update account_pay set status='CONFIRMED' where id = #{id}")
    public int confirmPay(Pay pay);

    //付款单失败
    @Update("update account_pay set status='Failed' where id = #{id}")
    public int cancelPay(Pay pay);



}
