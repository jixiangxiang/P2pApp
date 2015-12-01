package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/1.
 */
public class MerchantBean extends EBaseModel {
    private int merchantLogo;
    private String merchantName;
    private String merchantAddress;
    private String distance;

    public MerchantBean() {
    }

    public MerchantBean(int merchantLogo, String merchantName, String merchantAddress, String distance) {
        this.merchantLogo = merchantLogo;
        this.merchantName = merchantName;
        this.merchantAddress = merchantAddress;
        this.distance = distance;
    }

    public int getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(int merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
