package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/25.
 */
public class FundsRecordBean extends EBaseModel {
    private String fundsType;
    private String addFunds;
    private String deleteFunds;
    private String fundsTime;

    public FundsRecordBean() {
    }

    public FundsRecordBean(String fundsType, String addFunds, String deleteFunds, String fundsTime) {
        this.fundsType = fundsType;
        this.addFunds = addFunds;
        this.deleteFunds = deleteFunds;
        this.fundsTime = fundsTime;
    }

    public String getFundsType() {
        return fundsType;
    }

    public void setFundsType(String fundsType) {
        this.fundsType = fundsType;
    }

    public String getAddFunds() {
        return addFunds;
    }

    public void setAddFunds(String addFunds) {
        this.addFunds = addFunds;
    }

    public String getDeleteFunds() {
        return deleteFunds;
    }

    public void setDeleteFunds(String deleteFunds) {
        this.deleteFunds = deleteFunds;
    }

    public String getFundsTime() {
        return fundsTime;
    }

    public void setFundsTime(String fundsTime) {
        this.fundsTime = fundsTime;
    }
}
