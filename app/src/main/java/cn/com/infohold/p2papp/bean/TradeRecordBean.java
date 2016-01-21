package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/24.
 */
public class TradeRecordBean extends EBaseModel {
    private int trs_flag;//0为支出，1为收入
    private String trs_name;
    private String trs_date;
    private String amount;
    private String avai_balance;

    public TradeRecordBean() {
    }

    public void setTrs_flag(int trs_flag) {
        this.trs_flag = trs_flag;
    }

    public String getTrs_name() {
        return trs_name;
    }

    public void setTrs_name(String trs_name) {
        this.trs_name = trs_name;
    }

    public void setTrs_date(String trs_date) {
        this.trs_date = trs_date;
    }

    public String getAmount() {
        if (trs_flag == 0) {
            return "-" + amount;
        } else
            return "+" + amount;
    }

    public int getTrs_flag() {
        return trs_flag;
    }

    public String getTrs_date() {
        return trs_date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAvai_balance() {
        return "可用余额（元）:" + avai_balance;
    }

    public void setAvai_balance(String avai_balance) {
        this.avai_balance = avai_balance;
    }
}
