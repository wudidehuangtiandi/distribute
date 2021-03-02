package com.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 付款单
 * @author GC
 * @date 2020年 02月07日 11:12:36
 */
public class Pay implements Serializable {

    private int id;
    private String name;
    private String status;
    private BigDecimal paynum;

    @Override
    public String toString() {
        return "Pay{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", paynum=" + paynum +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
