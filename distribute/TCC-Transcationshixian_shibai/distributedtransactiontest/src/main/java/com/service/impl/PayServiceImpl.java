package com.service.impl;

import com.dao.PayDao;
import com.dao.ShoppingDao;
import com.domain.Commodity;
import com.domain.Order;
import com.domain.Pay;
import com.service.PayService;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 支付服务实现类
 *
 * @author GC
 * @date 2020年 02月07日 11:08:43
 */
@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PayDao payDao;
    @Autowired
    private ShoppingDao shoppingDao;

    @Compensable(confirmMethod = "payConfirm", cancelMethod = "payCancel", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    @Override
    public void pay(Order order) {
        //扣除zhang指定的钱
        Commodity commodity = shoppingDao.findCommodity(order.getCommodityno());
        int paymoney = payDao.paymoney(commodity.getCost());

        //付款待确认
        Pay pay = new Pay();
        //默认为张
        pay.setName("zhang");
        pay.setPaynum(commodity.getCost());
        pay.setStatus("PAYING");
        int i = payDao.payList(pay);

        //更改订单状态为支付中,插入订单支付单号
        order.setPayno(pay.getId());
        shoppingDao.updateOrderStatus(order);


    }
    @Transactional
    @Override
    public void payConfirm(Order order) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================================");
        System.out.println("=================mysql:payconfirm");
        System.out.println("============================================================================");


        int payno = order.getPayno();
        if (payno != 0) {
            Pay pay = payDao.findPayStatus(order);
            if ("PAYING".equals(pay.getStatus())) {
                //确认付款单
                int i = payDao.confirmPay(pay);
            }
        }


    }
    @Transactional
    @Override
    public void payCancel(Order order) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================================");
        System.out.println("=================mysql:paycancel");
        System.out.println("============================================================================");


        int payno = order.getPayno();
        if (payno != 0) {
            Pay pay = payDao.findPayStatus(order);
            if ("PAYING".equals(pay.getStatus())) {
                //付款失败
                int i = payDao.cancelPay(pay);
                //回滚zhang的扣款
                Commodity commodity = shoppingDao.findCommodity(order.getCommodityno());
                payDao.rollbackmoney(commodity.getCost());
            }
        }


    }
}
