package com.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单
 *
 * @author GC
 * @date 2020年 02月07日 10:11:25
 */
public class Order implements Serializable {
    private int id;
    private String orderno;
    private int userno;
    private String status;
    private BigDecimal paynum;
    private int commodityno;
    private int payno;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderno='" + orderno + '\'' +
                ", userno=" + userno +
                ", status='" + status + '\'' +
                ", paynum=" + paynum +
                ", commodityno=" + commodityno +
                ", payno=" + payno +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public int getUserno() {
        return userno;
    }

    public void setUserno(int userno) {
        this.userno = userno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPaynum() {
        return paynum;
    }

    public void setPaynum(BigDecimal paynum) {
        this.paynum = paynum;
    }

    public int getCommodityno() {
        return commodityno;
    }

    public void setCommodityno(int commodityno) {
        this.commodityno = commodityno;
    }

    public int getPayno() {
        return payno;
    }

    public void setPayno(int payno) {
        this.payno = payno;
    }
}
