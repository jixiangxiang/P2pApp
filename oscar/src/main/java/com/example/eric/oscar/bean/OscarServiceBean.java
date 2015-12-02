package com.example.eric.oscar.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/2.
 */
public class OscarServiceBean extends EBaseModel {
    private Integer serviceIcon;
    private String serviceName;

    public OscarServiceBean() {
    }

    public OscarServiceBean(Integer serviceIcon, String serviceName) {
        this.serviceIcon = serviceIcon;
        this.serviceName = serviceName;
    }

    public Integer getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(Integer serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}