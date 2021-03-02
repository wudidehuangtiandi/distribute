package com.service;

import com.domain.Order;
import org.mengyun.tcctransaction.api.Compensable;

public interface PayService {

    @Compensable
    public void pay(Order order);

    public void payConfirm(Order order);

    public void payCancel(Order order);
}
