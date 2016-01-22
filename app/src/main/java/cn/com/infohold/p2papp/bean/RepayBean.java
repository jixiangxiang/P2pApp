package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2016/1/22.
 */
public class RepayBean extends EBaseModel {
    private String repay_date;
    private String repay_total_amount;
    private String repay_principal;
    private String repay_interest;
    private String repay_def_int;
    private String repay_period;
    private String unpaid_total_amount;
    private String unpaid_principle_n;
    private String unpaid_interest_n;
    private String unpaid_def_int_n;

    public String getRepay_period() {
        return repay_period;
    }

    public void setRepay_period(String repay_period) {
        this.repay_period = repay_period;
    }

    public String getUnpaid_total_amount() {
        return unpaid_total_amount;
    }

    public void setUnpaid_total_amount(String unpaid_total_amount) {
        this.unpaid_total_amount = unpaid_total_amount;
    }

    public String getUnpaid_principle_n() {
        return unpaid_principle_n;
    }

    public void setUnpaid_principle_n(String unpaid_principle_n) {
        this.unpaid_principle_n = unpaid_principle_n;
    }

    public String getUnpaid_interest_n() {
        return unpaid_interest_n;
    }

    public void setUnpaid_interest_n(String unpaid_interest_n) {
        this.unpaid_interest_n = unpaid_interest_n;
    }

    public String getUnpaid_def_int_n() {
        return unpaid_def_int_n;
    }

    public void setUnpaid_def_int_n(String unpaid_def_int_n) {
        this.unpaid_def_int_n = unpaid_def_int_n;
    }

    public String getRepay_date() {
        return repay_date;
    }

    public void setRepay_date(String repay_date) {
        this.repay_date = repay_date;
    }

    public String getRepay_total_amount() {
        return repay_total_amount;
    }

    public void setRepay_total_amount(String repay_total_amount) {
        this.repay_total_amount = repay_total_amount;
    }

    public String getRepay_principal() {
        return repay_principal;
    }

    public void setRepay_principal(String repay_principal) {
        this.repay_principal = repay_principal;
    }

    public String getRepay_interest() {
        return repay_interest;
    }

    public void setRepay_interest(String repay_interest) {
        this.repay_interest = repay_interest;
    }

    public String getRepay_def_int() {
        return repay_def_int;
    }

    public void setRepay_def_int(String repay_def_int) {
        this.repay_def_int = repay_def_int;
    }
}
