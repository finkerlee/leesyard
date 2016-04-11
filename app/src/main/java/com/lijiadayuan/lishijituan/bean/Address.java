package com.lijiadayuan.lishijituan.bean;

/**
 * Created by lifanqiao on 16/3/24.
 */
public class Address {

    private String addId;
    private String addName;
    private String addPhone;
    private int addProvince;
    private int addCity;
    private int addArea;
    private String addDetail;
    private int addShow;
    private int addShopped;
    private String adduser;

    public Address() {
        super();
    }

    public Address(String addId, String addName, String addPhone, int addProvince, int addCity, int addArea, String addDetail, int addShow, int addShopped, String adduser) {
        this.addId = addId;
        this.addName = addName;
        this.addPhone = addPhone;
        this.addProvince = addProvince;
        this.addCity = addCity;
        this.addArea = addArea;
        this.addDetail = addDetail;
        this.addShow = addShow;
        this.addShopped = addShopped;
        this.adduser = adduser;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddPhone() {
        return addPhone;
    }

    public void setAddPhone(String addPhone) {
        this.addPhone = addPhone;
    }

    public int getAddProvince() {
        return addProvince;
    }

    public void setAddProvince(int addProvince) {
        this.addProvince = addProvince;
    }

    public int getAddCity() {
        return addCity;
    }

    public void setAddCity(int addCity) {
        this.addCity = addCity;
    }

    public int getAddArea() {
        return addArea;
    }

    public void setAddArea(int addArea) {
        this.addArea = addArea;
    }

    public String getAddDetail() {
        return addDetail;
    }

    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail;
    }

    public int getAddShow() {
        return addShow;
    }

    public void setAddShow(int addShow) {
        this.addShow = addShow;
    }

    public int getAddShopped() {
        return addShopped;
    }

    public void setAddShopped(int addShopped) {
        this.addShopped = addShopped;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }
}
