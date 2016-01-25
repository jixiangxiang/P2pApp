package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/29.
 */
public class OscarBean extends EBaseModel {
    private int id;
    private String authAcct;
    private String bindDate;
    private String cardNo;
    private Double balance;
    private Boolean locked;
    private Boolean select;

    public OscarBean() {
    }

    public OscarBean(String authAcct, String bindDate, Double balance) {
        this.authAcct = authAcct;
        this.bindDate = bindDate;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthAcct() {
        return authAcct;
    }

    public void setAuthAcct(String authAcct) {
        this.authAcct = authAcct;
    }

    public String getBindDate() {
        return bindDate;
    }

    public void setBindDate(String bindDate) {
        this.bindDate = bindDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getSelect() {
        if (select == null)
            select = false;
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
