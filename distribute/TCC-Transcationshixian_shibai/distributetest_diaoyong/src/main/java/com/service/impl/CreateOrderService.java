package com.service.impl;

import com.dao.ShoppingDao;
import com.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 创建订单
 *
 * @author GC
 * @date 2020年 02月07日 13:53:44
 */
@Service
public class CreateOrderService {
    @Autowired
    private ShoppingDao shoppingDao;
    @Autowired
    private ShoppingServiceImpl shoppingService;

    public void createorders(Order order){
        shoppingDao.createOrder(order);
        try {
            shoppingService.paymoney(order);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
