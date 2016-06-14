package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/6.
 */
public class JdCardBean extends EBaseModel {
    private Double cardFace;
    private Integer cardCount;
    private Integer selectCount;

    public JdCardBean() {
    }

    public JdCardBean(Double bar, Integer count) {
        this.cardFace = bar;
        this.cardCount = count;
    }

    public Double getCardFace() {
        return cardFace;
    }

    public void setCardFace(Double cardFace) {
        this.cardFace = cardFace;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getSelectCount() {
        if (selectCount == null)
            selectCount = 0;
        return selectCount;
    }

    public void setSelectCount(Integer selectCount) {
        this.selectCount = selectCount;
    }
}
