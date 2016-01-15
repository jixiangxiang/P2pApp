package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2016/1/14.
 */
public class InvestProductBean extends EBaseModel {
    private String productName;
    private String rateYear;
    private String loanLimit;
    private String loanMoney;
    private String toObject;
    private String leastMoney;

    public InvestProductBean() {
    }

    public InvestProductBean(String productName, String rateYear, String loanLimit, String loanMoney, String toObject, String leastMoney) {
        this.productName = productName;
        this.rateYear = rateYear;
        this.loanLimit = loanLimit;
        this.loanMoney = loanMoney;
        this.toObject = toObject;
        this.leastMoney = leastMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRateYear() {
        return rateYear;
    }

    public void setRateYear(String rateYear) {
        this.rateYear = rateYear;
    }

    public String getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getToObject() {
        return toObject;
    }

    public void setToObject(String toObject) {
        this.toObject = toObject;
    }

    public String getLeastMoney() {
        return leastMoney;
    }

    public void setLeastMoney(String leastMoney) {
        this.leastMoney = leastMoney;
    }
}
