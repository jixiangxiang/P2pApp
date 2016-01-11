package cn.com.infohold.p2papp.bean;

import java.text.DecimalFormat;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/25.
 */
public class RepayPlanBean extends EBaseModel {
    private String stageno;
    private String repaydate;
    private String repayprincipal;
    private String repayinterest;
    private String totalMoney;

    public String getStageno() {
        return stageno;
    }

    public void setStageno(String stageno) {
        this.stageno = stageno;
    }

    public String getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(String repaydate) {
        this.repaydate = repaydate;
    }

    public String getRepayprincipal() {
        return repayprincipal;
    }

    public void setRepayprincipal(String repayprincipal) {
        this.repayprincipal = repayprincipal;
    }

    public String getRepayinterest() {
        return repayinterest;
    }

    public void setRepayinterest(String repayinterest) {
        this.repayinterest = repayinterest;
    }

    public String getTotalMoney() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.valueOf(repayprincipal) + Double.valueOf(repayinterest)).toString();
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
