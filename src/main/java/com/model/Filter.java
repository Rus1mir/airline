package com.model;

import java.util.Date;

public class Filter {

    private Date dateAfter;
    private Date dateBefore;
    private String cityDepart;
    private String cityDest;
    private String planeName;

    public Filter(Date dateFrom, Date dateBefore, String cityDepart, String cityDest, String planeName) {
        this.dateAfter = dateFrom;
        this.dateBefore = dateBefore;
        this.cityDepart = cityDepart;
        this.cityDest = cityDest;
        this.planeName = planeName;
    }

    public Filter() {
        this.dateAfter = new Date();
        this.dateBefore = new Date();
        this.cityDepart = "City departure";
        this.cityDest = "City destination";
        this.planeName = "Plane model";
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public String getCityDepart() {
        return cityDepart;
    }

    public String getCityDest() {
        return cityDest;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }
}
