package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class TransferProjectBean extends EBaseModel {
    private Integer id;
    private String assignmentseq;
    private String projectname;
    private Double rate;
    private String loanno;
    private String residualterm;
    private String transferprince;
    private String incomeway;
    private Double duebalance;
    private Integer usertype;
    private String begindate;
    private String assignmentstatus;
    private String assignmentpricevalue;
    private String issumday;
    private String allLimit;

    public TransferProjectBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssignmentseq() {
        return assignmentseq;
    }

    public void setAssignmentseq(String assignmentseq) {
        this.assignmentseq = assignmentseq;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public String getResidualterm() {
        return residualterm;
    }

    public void setResidualterm(String residualterm) {
        this.residualterm = residualterm;
    }

    public String getTransferprince() {
        return transferprince;
    }

    public void setTransferprince(String transferprince) {
        this.transferprince = transferprince;
    }

    public String getIncomeway() {
        return incomeway;
    }

    public void setIncomeway(String incomeway) {
        this.incomeway = incomeway;
    }

    public Double getDuebalance() {
        return duebalance;
    }

    public void setDuebalance(Double duebalance) {
        this.duebalance = duebalance;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getAssignmentstatus() {
        return assignmentstatus;
    }

    public void setAssignmentstatus(String assignmentstatus) {
        this.assignmentstatus = assignmentstatus;
    }

    public String getAssignmentpricevalue() {
        return assignmentpricevalue;
    }

    public void setAssignmentpricevalue(String assignmentpricevalue) {
        this.assignmentpricevalue = assignmentpricevalue;
    }

    public String getIssumday() {
        return issumday;
    }

    public void setIssumday(String issumday) {
        this.issumday = issumday;
    }

    public String getAllLimit() {

        return issumday + "/" + residualterm;
    }

    public void setAllLimit(String allLimit) {
        this.allLimit = allLimit;
    }
}
