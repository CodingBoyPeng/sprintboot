package com.pf.springboot.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 公司实体
 * @author:Peng
 * @Date:2021/1/15
 */
public class Company {

    /**
     * 公司ID
     */
    private Integer id;

    /**
     * 老板名称
     */
    private String bossName;

    /**
     * 公司规模
     */
    private Integer size;

    /**
     * 公司成立时间
     */
    private Date createTime;

    /**
     * 注册基金
     */
    private BigDecimal registerMoney;

    public Company(Integer id, String bossName, Integer size, Date createTime, BigDecimal registerMoney) {
        this.id = id;
        this.bossName = bossName;
        this.size = size;
        this.createTime = createTime;
        this.registerMoney = registerMoney;
    }

    public Company() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(BigDecimal registerMoney) {
        this.registerMoney = registerMoney;
    }


    public String echoCompanyDetail(String bossName, Integer size, Date createTime, BigDecimal registerMoney) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return "(●'◡'●)ﾉ【pic】(●'◡'●)ﾉ【pic】(●'◡'●)ﾉ【pic】 \n 这哩呀》》》" +
                "公司老板名称: " + bossName + "\n" +
                "规模: " + size + "\n" +
                "创立时间: " + format.format(createTime) + "\n" +
                "注册资金: " + registerMoney + "\n￣□￣｜｜";
    }
}
