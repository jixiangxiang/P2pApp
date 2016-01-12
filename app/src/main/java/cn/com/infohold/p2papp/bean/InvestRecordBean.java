package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/17.
 */
public class InvestRecordBean extends EBaseModel {
    private String total_count;
    private String userid;
    private String investtime;
    private String investamount;

    public InvestRecordBean() {
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getInvesttime() {
        return investtime;
    }

    public void setInvesttime(String investtime) {
        this.investtime = investtime;
    }

    public String getInvestamount() {
        return investamount;
    }

    public void setInvestamount(String investamount) {
        this.investamount = investamount;
    }
}
