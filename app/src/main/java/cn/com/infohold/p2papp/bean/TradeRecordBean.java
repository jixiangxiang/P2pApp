package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/24.
 */
public class TradeRecordBean extends EBaseModel {
    private String tradeType;
    private String tradeDate;
    private String tradeMoney;
    private String balanceMoeny;

    public TradeRecordBean() {
    }

    public TradeRecordBean(String tradeType, String tradeDate, String tradeMoney, String balanceMoeny) {
        this.tradeType = tradeType;
        this.tradeDate = tradeDate;
        this.tradeMoney = tradeMoney;
        this.balanceMoeny = balanceMoeny;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(String tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getBalanceMoeny() {
        return balanceMoeny;
    }

    public void setBalanceMoeny(String balanceMoeny) {
        this.balanceMoeny = balanceMoeny;
    }
}
