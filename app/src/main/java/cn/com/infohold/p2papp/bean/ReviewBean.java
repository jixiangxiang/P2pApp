package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/11/16.
 */
public class ReviewBean extends EBaseModel {
    private Integer id;
    private String reviewName;

    public ReviewBean() {
    }

    public ReviewBean(String reviewName) {
        this.reviewName = reviewName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }
}
