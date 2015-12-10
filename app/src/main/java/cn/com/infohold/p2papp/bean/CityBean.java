package cn.com.infohold.p2papp.bean;

import common.eric.com.ebaselibrary.model.EBaseModel;

/**
 * Created by eric on 2015/12/10.
 */
public class CityBean extends EBaseModel {
    private String openingCity;
    private String cityname;

    public String getOpeningCity() {
        return openingCity;
    }

    public void setOpeningCity(String openingCity) {
        this.openingCity = openingCity;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}