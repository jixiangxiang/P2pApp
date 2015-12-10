package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/10.
 */
public class ProvinceBean extends EBaseModel {
    private String openingProvince;
    private String provincename;

    public String getOpeningProvince() {
        return openingProvince;
    }

    public void setOpeningProvince(String openingProvince) {
        this.openingProvince = openingProvince;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }
}