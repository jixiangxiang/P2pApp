package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/4.
 */
public class MerchantCateBean extends EBaseModel {
    private String id;
    private String tag;
    private Boolean select;

    public MerchantCateBean() {
    }

    public MerchantCateBean(String tag, String id) {
        this.tag = tag;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
