package com.dao;

import com.domain.Commodity;
import com.domain.Order;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//订单和商品有关的查询
public interface ShoppingDao {

    @Select("select * from account_commodity where id=#{id}")
    public Commodity findCommodity(Integer id);

    @Update("update account_order set status='PAYING',payno=${payno} where id=#{id}")
    public int updateOrderStatus(Order order);

    @Select("select * from account_order where id =#{id}")
    public Order findOrderStatus(Integer id);



}
