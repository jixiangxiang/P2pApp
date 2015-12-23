package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class TransFerringBean extends EBaseModel {
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
    private Integer status;

    public TransFerringBean() {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
