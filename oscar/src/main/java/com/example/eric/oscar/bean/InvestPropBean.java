package com.example.eric.oscar.bean;

import java.util.Date;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2016/1/27.
 */
public class InvestPropBean extends EBaseModel {
    private String cpId;
    private String coupon;
    private Date eUseDate;
    private String faceLimit;
    private String faceValue;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCoupon() {
        return "道具：" + coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Date geteUseDate() {
        return eUseDate;
    }

    public void seteUseDate(Date eUseDate) {
        this.eUseDate = eUseDate;
    }

    public String getFaceLimit() {
        return "起投投资额：" + faceLimit;
    }

    public void setFaceLimit(String faceLimit) {
        this.faceLimit = faceLimit;
    }

    public String getFaceValue() {
        return "面值" + faceValue + "元 ";
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }
}
