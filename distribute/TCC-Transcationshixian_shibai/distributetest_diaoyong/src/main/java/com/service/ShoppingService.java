package com.service;

import com.domain.Order;
import org.mengyun.tcctransaction.api.Compensable;

/**
 * @author GC
 * @date 2020年 02月07日 10:30:30
 */
public interface ShoppingService {

    public void paymoney(Order order);

    public void orderConfirm(Order order);

    public void orderCancel(Order order);

}
