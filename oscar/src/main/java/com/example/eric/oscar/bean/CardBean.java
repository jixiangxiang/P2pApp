package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/6.
 */
public class CardBean extends EBaseModel {
    private Double bar;
    private Integer count;

    public CardBean() {
    }

    public CardBean(Double bar, Integer count) {
        this.bar = bar;
        this.count = count;
    }

    public Double getBar() {
        return bar;
    }

    public void setBar(Double bar) {
        this.bar = bar;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
