package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class LoanProjectBean extends EBaseModel {
    private Integer id;
    private String loan_no;
    private String projectname;
    private String applydate;
    private String loanstdate;
    private String paid_pi;
    private String unpaid_pi;
    private String unpaidperiod;
    private String debtdate;
    private String loanamt;
    private String repayway;
    private String loanrate;
    private String nextrepaydate;
    private String remainperiod;
    private String totalperiod;
    private String limitPeriod;
    private String repayamtperiodly;
    private String projectno;
    private Integer status;
    private Integer usertype;

    public LoanProjectBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoan_no() {
        return loan_no;
    }

    public void setLoan_no(String loan_no) {
        this.loan_no = loan_no;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
    }

    public String getLoanstdate() {
        return loanstdate;
    }

    public void setLoanstdate(String loanstdate) {
        this.loanstdate = loanstdate;
    }

    public String getPaid_pi() {
        return paid_pi;
    }

    public void setPaid_pi(String paid_pi) {
        this.paid_pi = paid_pi;
    }

    public String getUnpaid_pi() {
        return unpaid_pi;
    }

    public void setUnpaid_pi(String unpaid_pi) {
        this.unpaid_pi = unpaid_pi;
    }

    public String getUnpaidperiod() {
        return unpaidperiod;
    }

    public void setUnpaidperiod(String unpaidperiod) {
        this.unpaidperiod = unpaidperiod;
    }

    public String getDebtdate() {
        return debtdate;
    }

    public void setDebtdate(String debtdate) {
        this.debtdate = debtdate;
    }

    public String getLoanamt() {
        return loanamt;
    }

    public void setLoanamt(String loanamt) {
        this.loanamt = loanamt;
    }

    public String getRepayway() {
        switch (Integer.valueOf(repayway)) {
            case 1:
                repayway = "等额本息";
                break;
            case 2:
                repayway = "等额本金";
                break;
            case 3:
                repayway = "按月付息，一次还本";
                break;
            case 4:
                repayway = "利随本清";
                break;
            default:
                repayway = "等额本金";
                break;
        }
        return repayway;
    }

    public String getLimitPeriod() {
        return remainperiod + "/" + totalperiod;
    }

    public void setLimitPeriod(String limitPeriod) {
        this.limitPeriod = limitPeriod;
    }

    public void setRepayway(String repayway) {
        this.repayway = repayway;
    }

    public String getLoanrate() {
        return loanrate;
    }

    public void setLoanrate(String loanrate) {
        this.loanrate = loanrate;
    }

    public String getNextrepaydate() {
        return nextrepaydate;
    }

    public void setNextrepaydate(String nextrepaydate) {
        this.nextrepaydate = nextrepaydate;
    }

    public String getRemainperiod() {
        return remainperiod;
    }

    public void setRemainperiod(String remainperiod) {
        this.remainperiod = remainperiod;
    }

    public String getTotalperiod() {
        return totalperiod;
    }

    public void setTotalperiod(String totalperiod) {
        this.totalperiod = totalperiod;
    }

    public String getRepayamtperiodly() {
        return repayamtperiodly;
    }

    public void setRepayamtperiodly(String repayamtperiodly) {
        this.repayamtperiodly = repayamtperiodly;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }
}
