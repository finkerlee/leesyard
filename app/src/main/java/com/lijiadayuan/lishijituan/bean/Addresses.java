package com.lijiadayuan.lishijituan.bean;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lee on 2016-03-17.
 * E-mail:johnyylee@163.com
 *
 * 封装addresses表数据的类
 */
public class Addresses implements Parcelable{

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

    protected Addresses(Parcel in) {
        addId = in.readString();
        addName = in.readString();
        addPhone = in.readString();
        addProvince = in.readString();
        addCity = in.readString();
        addArea = in.readString();
        addDetail = in.readString();
        addShow = in.readInt();
        addShopped = in.readInt();
        addUser = in.readString();
    }

    public static final Creator<Addresses> CREATOR = new Creator<Addresses>() {
        @Override
        public Addresses createFromParcel(Parcel in) {
            return new Addresses(in);
        }

        @Override
        public Addresses[] newArray(int size) {
            return new Addresses[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addId);
        dest.writeString(addName);
        dest.writeString(addPhone);
        dest.writeString(addProvince);
        dest.writeString(addCity);
        dest.writeString(addArea);
        dest.writeString(addDetail);
        dest.writeInt(addShow);
        dest.writeInt(addShopped);
        dest.writeString(addUser);
    }
}
