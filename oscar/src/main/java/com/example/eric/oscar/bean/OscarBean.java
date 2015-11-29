package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/29.
 */
public class OscarBean extends EBaseModel {
    private String cardNo;
    private String bindDate;
    private Double balance;

    public OscarBean() {
    }

    public OscarBean(String cardNo, String bindDate, Double balance) {
        this.cardNo = cardNo;
        this.bindDate = bindDate;
        this.balance = balance;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
