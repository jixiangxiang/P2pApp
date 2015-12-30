package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/14.
 */
public class SelfInvestBean extends EBaseModel {
    private String project_name;
    private String project_rate;
    private String invest_amount;
    private String loan_time_interval;
    private String loan_time_interval_type;
    private String begin_date;
    private String receivable_amount;
    private int repay_way;
    private String publish_date;
    private String process;
    private String due_date;
    private String due_period;
    private String total_period;
    private String loan_no;
    private String cif_seq;
    private String project_status;
    private String periodRate;
    private String usertype;

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_rate() {
        return project_rate;
    }

    public void setProject_rate(String project_rate) {
        this.project_rate = project_rate;
    }

    public String getInvest_amount() {
        return invest_amount;
    }

    public void setInvest_amount(String invest_amount) {
        this.invest_amount = invest_amount;
    }

    public String getLoan_time_interval() {
        return loan_time_interval;
    }

    public void setLoan_time_interval(String loan_time_interval) {
        this.loan_time_interval = loan_time_interval;
    }

    public String getLoan_time_interval_type() {
        return loan_time_interval_type;
    }

    public void setLoan_time_interval_type(String loan_time_interval_type) {
        this.loan_time_interval_type = loan_time_interval_type;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getReceivable_amount() {
        return receivable_amount;
    }

    public void setReceivable_amount(String receivable_amount) {
        this.receivable_amount = receivable_amount;
    }

    public int getRepay_way() {
        return repay_way;
    }

    public void setRepay_way(int repay_way) {
        this.repay_way = repay_way;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getDue_period() {
        return due_period;
    }

    public void setDue_period(String due_period) {
        this.due_period = due_period;
    }

    public String getTotal_period() {
        return total_period;
    }

    public void setTotal_period(String total_period) {
        this.total_period = total_period;
    }

    public String getLoan_no() {
        return loan_no;
    }

    public void setLoan_no(String loan_no) {
        this.loan_no = loan_no;
    }

    public String getCif_seq() {
        return cif_seq;
    }

    public void setCif_seq(String cif_seq) {
        this.cif_seq = cif_seq;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPeriodRate() {
        return due_period + "/" + total_period;
    }

    public void setPeriodRate(String periodRate) {
        this.periodRate = periodRate;
    }
}
