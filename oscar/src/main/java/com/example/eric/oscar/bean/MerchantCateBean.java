package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/4.
 */
public class MerchantCateBean extends EBaseModel {
    private String tag;
    private Boolean select;

    public MerchantCateBean() {
    }

    public MerchantCateBean(String tag, boolean isSelect) {
        this.tag = tag;
        this.select = isSelect;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean isSelect() {
        return select == null ? false : select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
