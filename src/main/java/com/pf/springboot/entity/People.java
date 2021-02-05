package com.pf.springboot.entity;

import java.math.BigDecimal;

/**
 * @description: 人物实体
 * @author:Peng
 * @Date:2021/1/15
 */
public class People {

    private String name;

    private Integer age;

    private BigDecimal money;

    protected String address;

    public String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public People(String name, Integer age, BigDecimal money, String address, String telephone) {
        this.name = name;
        this.age = age;
        this.money = money;
        this.address = address;
        this.telephone = telephone;
    }
}
