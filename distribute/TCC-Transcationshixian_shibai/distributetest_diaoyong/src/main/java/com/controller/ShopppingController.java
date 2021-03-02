package com.controller;

import com.dao.ShoppingDao;
import com.domain.Commodity;
import com.domain.Order;
import com.service.impl.CreateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * 买东西
 *
 * @author GC
 * @date 2020年 02月07日 10:23:49
 */
@Controller
@RequestMapping("/shop")
public class ShopppingController {

    @Autowired
    private ShoppingDao shoppingDao;
    @Autowired
    private CreateOrderService createOrderService;

    @RequestMapping("/buy/{id}")
    public ModelAndView buyThings(@PathVariable(name="id")String s){
        ModelAndView modelAndView = new ModelAndView();
        //创建订单并自动付款，客户默认为zhang
        Commodity commodity = shoppingDao.findCommodity(s);
        Order order = new Order();
        order.setStatus("DEFAULT");
        order.setCommodityno(commodity.getId());
        order.setOrderno(UUID.randomUUID().toString().substring(0,8));
        //默认为zhang
        order.setUserno(1);
        order.setPaynum(commodity.getCost());
        createOrderService.createorders(order);

        modelAndView.setViewName("list");


        return modelAndView;
    }

}
