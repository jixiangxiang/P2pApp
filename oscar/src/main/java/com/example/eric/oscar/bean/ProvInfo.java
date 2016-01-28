package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2016/1/28.
 */
public class ProvInfo extends EBaseModel {
    private String id;
    private String prov;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }
}
