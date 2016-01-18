package com.example.eric.oscar.common;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/8/31.
 */
public class ResponseResult extends EBaseModel {
    private String return_message;
    private Integer return_code;
    private Object data;

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public Integer getReturn_code() {
        return return_code;
    }

    public void setReturn_code(Integer return_code) {
        this.return_code = return_code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
