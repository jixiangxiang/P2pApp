package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/4.
 */
public class MerchantCateBean extends EBaseModel {
    private String cateName;
    private boolean select;

    public MerchantCateBean() {
    }

    public MerchantCateBean(String cateName, boolean isSelect) {
        this.cateName = cateName;
        this.select = isSelect;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
