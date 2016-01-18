package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class ToTransProjectBean extends EBaseModel {
    private Integer id;
    private String projectname;
    private String projectno;
    private String acno;
    private String rate;
    private String originvestamt;
    private String begindate;
    private String enddate;
    private String repaytype;
    private String limit;
    private String issuenum;
    private String projstatus;
    private Integer usertype;
    private String loanno;
    private String issuetype;

    public ToTransProjectBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOriginvestamt() {
        return originvestamt;
    }

    public void setOriginvestamt(String originvestamt) {
        this.originvestamt = originvestamt;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getRepaytype() {
        switch (Integer.valueOf(repaytype)) {
            case 1:
                repaytype = "等额本息";
                break;
            case 2:
                repaytype = "等额本金";
                break;
            case 3:
                repaytype = "按月付息，一次还本";
                break;
            case 4:
                repaytype = "利随本清";
                break;
            default:
                repaytype = "等额本金";
                break;
        }
        return repaytype;
    }

    public void setRepaytype(String repaytype) {
        this.repaytype = repaytype;
    }

    public String getLimit() {
        return String.valueOf(Double.valueOf(enddate) - Double.valueOf(begindate));
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(String issuenum) {
        this.issuenum = issuenum;
    }

    public String getProjstatus() {
        return projstatus;
    }

    public void setProjstatus(String projstatus) {
        this.projstatus = projstatus;
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

    public String getIssuetype() {
        String issuetypeText = "";
        if (issuetype.equals("Y")) {
            issuetypeText = "还款期限   (  年  )";
        } else if (issuetype.equals("M")) {
            issuetypeText = "还款期限   (  月  )";
        } else if (issuetype.equals("D")) {
            issuetypeText = "还款期限   (  天  )";
        }
        return issuetypeText;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }
}
