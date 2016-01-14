package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class InvestProjectBean extends EBaseModel {
    private Integer id;
    private Double rate;
    private String balance;
    private Integer issuenum;
    private String descript;
    private String status;
    private String nowstatus;
    private Integer usertype;
    private String loanno;
    private String cif_seq;
    private String projectname;
    private String issuetype;
    private String assignmentseq;
    private String projectno;

    public InvestProjectBean() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(Integer issuenum) {
        this.issuenum = issuenum;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNowstatus() {
        return nowstatus;
    }

    public void setNowstatus(String nowstatus) {
        this.nowstatus = nowstatus;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public String getCif_seq() {
        return cif_seq;
    }

    public void setCif_seq(String cif_seq) {
        this.cif_seq = cif_seq;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getIssuetype() {
        if (issuetype.equals("Y")) {
            issuetype = "还款期限   (  年  )";
        } else if (issuetype.equals("M")) {
            issuetype = "还款期限   (  月  )";
        } else if (issuetype.equals("D")) {
            issuetype = "还款期限   (  天  )";
        }
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }

    public String getAssignmentseq() {
        return assignmentseq;
    }

    public void setAssignmentseq(String assignmentseq) {
        this.assignmentseq = assignmentseq;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }
}
