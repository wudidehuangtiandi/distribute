package com.domain;

import java.io.Serializable;

public class Account implements Serializable {
    private Integer id;
    private String name;
    private double account;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Account=" + account +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getaccount() {
        return account;
    }

    public void setaccount(double account) {
        this.account = account;
    }
}
