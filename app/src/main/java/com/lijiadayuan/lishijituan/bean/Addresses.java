package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-17.
 * E-mail:johnyylee@163.com
 *
 * 封装addresses表数据的类
 */
public class Addresses {

    private String addId;                        // 主键,流水号
    private String addName;                      // 收货人姓名
    private String addPhone;                     // 售货人手机号
    private String addProvince;                  // 省份id
    private String addCity;                      // 城市id
    private String addArea;                      // 区id
    private String addDetail;                    // 详细地址信息
    private int addShow;                         // 是否显示，0:不显示，1:显示。默认1
    private int addShopped;                      // 是否使用过，0:未使用，1：使用过。默认1
    private String addUser;                      // 地址所属用户,外键,与用户表主键关联


    public Addresses() {
        super();
    }

    public Addresses(String addId, String addName, String addPhone, String addProvince, String addCity, String addArea, String addDetail, int addShow, int addShopped, String addUser) {
        this.addId = addId;
        this.addName = addName;
        this.addPhone = addPhone;
        this.addProvince = addProvince;
        this.addCity = addCity;
        this.addArea = addArea;
        this.addDetail = addDetail;
        this.addShow = addShow;
        this.addShopped = addShopped;
        this.addUser = addUser;
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

    public String getAddProvince() {
        return addProvince;
    }

    public void setAddProvince(String addProvince) {
        this.addProvince = addProvince;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddArea() {
        return addArea;
    }

    public void setAddArea(String addArea) {
        this.addArea = addArea;
    }

    public String getAddDetail() {
        return addDetail;
    }

    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
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
}
