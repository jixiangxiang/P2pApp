package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class TransFerringBean extends EBaseModel {
    private Integer id;
    private String transfer_price;
    private String predict_profit;
    private String loan_due_date;
    private String begin_transfer_date;
    private String transfer_principal;
    private String assignmentseq;
    private String project_name;
    private String loan_no;
    private String cif_seq;
    private String project_status;
    private String usertype;

    public TransFerringBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPredict_profit() {
        return predict_profit;
    }

    public void setPredict_profit(String predict_profit) {
        this.predict_profit = predict_profit;
    }

    public String getLoan_due_date() {
        return loan_due_date;
    }

    public void setLoan_due_date(String loan_due_date) {
        this.loan_due_date = loan_due_date;
    }

    public String getBegin_transfer_date() {
        return begin_transfer_date;
    }

    public void setBegin_transfer_date(String begin_transfer_date) {
        this.begin_transfer_date = begin_transfer_date;
    }

    public String getTransfer_principal() {
        return transfer_principal;
    }

    public void setTransfer_principal(String transfer_principal) {
        this.transfer_principal = transfer_principal;
    }

    public String getAssignmentseq() {
        return assignmentseq;
    }

    public void setAssignmentseq(String assignmentseq) {
        this.assignmentseq = assignmentseq;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
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

    public String getTransfer_price() {
        return transfer_price;
    }

    public void setTransfer_price(String transfer_price) {
        this.transfer_price = transfer_price;
    }
}
