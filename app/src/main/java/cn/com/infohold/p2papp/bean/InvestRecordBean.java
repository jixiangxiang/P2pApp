package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/17.
 */
public class InvestRecordBean extends EBaseModel {
    private String index;
    private String projectName;
    private String investDate;
    private Double investMoney;

    public InvestRecordBean() {
    }

    public InvestRecordBean(String index, String projectName, String investDate, Double investMoney) {
        this.index = index;
        this.projectName = projectName;
        this.investDate = investDate;
        this.investMoney = investMoney;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInvestDate() {
        return investDate;
    }

    public void setInvestDate(String investDate) {
        this.investDate = investDate;
    }

    public Double getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(Double investMoney) {
        this.investMoney = investMoney;
    }
}
