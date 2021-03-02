package com.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品
 *
 * @author GC
 * @date 2020年 02月07日 10:37:20
 */
public class Commodity implements Serializable {

    private int id;
    private String name;
    private BigDecimal cost;

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
