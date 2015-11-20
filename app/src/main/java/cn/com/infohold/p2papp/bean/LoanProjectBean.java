package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/12.
 */
public class LoanProjectBean extends EBaseModel {
    private Integer id;
    private Double preYield;
    private Double investableMoney;
    private Integer limit;
    private String descript;
    private Integer status;

    public LoanProjectBean() {
    }

    public LoanProjectBean(Double preYield, Double investableMoney, Integer limit, Integer status) {
        this.preYield = preYield;
        this.investableMoney = investableMoney;
        this.limit = limit;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPreYield() {
        return preYield;
    }

    public void setPreYield(Double preYield) {
        this.preYield = preYield;
    }

    public Double getInvestableMoney() {
        return investableMoney;
    }

    public void setInvestableMoney(Double investableMoney) {
        this.investableMoney = investableMoney;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
