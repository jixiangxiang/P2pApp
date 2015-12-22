package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class InvestProjectBean extends EBaseModel {
    private Integer id;
    private Double rate;
    private Double balance;
    private Integer issuenum;
    private String descript;
    private String status;
    private Integer usertype;
    private String loanno;
    private String cif_seq;
    private String projectname;

    public InvestProjectBean() {
    }

    public InvestProjectBean(Double preYield, Double investableMoney, Integer limit, String status) {
        this.rate = preYield;
        this.balance = investableMoney;
        this.issuenum = limit;
        this.status = status;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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
}
