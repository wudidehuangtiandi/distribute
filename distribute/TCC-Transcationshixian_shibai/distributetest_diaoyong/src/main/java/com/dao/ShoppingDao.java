package com.dao;

import com.domain.Commodity;
import com.domain.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;


public interface ShoppingDao {

    //查询商品
     @Select("select * from account_commodity where id =#{id}")
     public Commodity findCommodity(String id);

    //创建订单
    @Insert("insert into account_order (orderno,userno,status,paynum,commodityno) values(#{orderno},#{userno},#{status},#{paynum},#{commodityno})")
    @SelectKey(before=false,keyProperty="id",resultType=Integer.class,statementType= StatementType.STATEMENT,statement="SELECT LAST_INSERT_ID() AS id")
    public int createOrder(Order order);

    //查询订单
    @Select("select * from account_order where id =#{id}")
    public Order findOrderStatus(Integer id);

    //确认订单
    @Update("update account_order set status='CONFIRMED' where id =#{id} ")
    public int confirmOrder(Integer id);

    //取消订单
    @Update("update account_order set status='FAILED' where id =#{id} ")
    public int cancelOrder(Integer id);
}
