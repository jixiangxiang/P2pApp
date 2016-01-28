package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2016/1/24.
 */
public class SelfInvestBean extends EBaseModel {
    private int id;
    private String name;
    private String profit;
    private String type;
    private String total;
    private String mini;
    private String duration;
    private String amt;
    private String interest;
    private String coupon;
    private String date;

    public String getInvtype() {
        return invtype;
    }

    public void setInvtype(String invtype) {
        this.invtype = invtype;
    }

    private String invtype;

    public String getInvdate() {
        return "投资时间：" + invdate;
    }

    public void setInvdate(String invdate) {
        this.invdate = invdate;
    }

    private String invdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return "项目：" + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfit() {
        return "年化利息：" + profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getType() {
        return "投资类型："+type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return "期限" + duration + "天";
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }

    public String getAmt() {
        return "投资金额：" + amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getInterest() {
        return "利息收益：" + interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCoupon() {
        return "理财金券收益：" + coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDate() {
        return "到期时间：" + date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
