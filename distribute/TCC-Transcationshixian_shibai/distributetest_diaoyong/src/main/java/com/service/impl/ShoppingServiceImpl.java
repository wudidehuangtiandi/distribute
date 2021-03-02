package com.service.impl;

import com.dao.ShoppingDao;
import com.domain.Commodity;
import com.domain.Order;
import com.service.PayService;
import com.service.ShoppingService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author GC
 * @date 2020年 02月07日 10:31:04
 */

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingDao shoppingDao;
    @Autowired
    private PayService payService;

    @Compensable(confirmMethod = "orderConfirm", cancelMethod = "orderCancel", transactionContextEditor = DubboTransactionContextEditor.class)

    public void paymoney(Order order) {

        //调用支付

            payService.pay(order);


    }

    public void orderConfirm(Order order){
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================================");
        System.out.println("=================mysql:orderconfirm");
        System.out.println("============================================================================");
        Order orderStatus = shoppingDao.findOrderStatus(order.getId());
        if(orderStatus!=null&&"PAYING".equals(orderStatus.getStatus())){
            //确认订单
            shoppingDao.confirmOrder(orderStatus.getId());
        }

    }

    public void orderCancel(Order order){
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================================");
        System.out.println("=================mysql:ordercancel");
        System.out.println("============================================================================");
        Order orderStatus = shoppingDao.findOrderStatus(order.getId());
        if(orderStatus!=null&&"PAYING".equals(orderStatus.getStatus())){
            //取消订单
            shoppingDao.cancelOrder(orderStatus.getId());
        }
    }
}
