package com.lijiadayuan.lishijituan.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lijiadayuan.lishijituan.http.UrlConstants;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/27.
 */
public class ProductViewBean implements Parcelable{
    private ArrayList<String> picList;
    private String goodsName;
    private String goodsNum;
    private String goodsSpec;
    private String goodsPrice;
    private int goodsType;
    private String goodsInfoUrl;

    public ProductViewBean(){

    }

    protected ProductViewBean(Parcel in) {
        picList = in.createStringArrayList();
        goodsName = in.readString();
        goodsNum = in.readString();
        goodsSpec = in.readString();
        goodsPrice = in.readString();
        goodsType = in.readInt();
        goodsInfoUrl = in.readString();
    }

    public static final Creator<ProductViewBean> CREATOR = new Creator<ProductViewBean>() {
        @Override
        public ProductViewBean createFromParcel(Parcel in) {
            return new ProductViewBean(in);
        }

        @Override
        public ProductViewBean[] newArray(int size) {
            return new ProductViewBean[size];
        }
    };

    public void setPicList(ArrayList<String> picList) {
        this.picList = picList;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setGoodsInfoUrl(String goodsInfoUrl) {
        this.goodsInfoUrl = goodsInfoUrl;
    }

    public ArrayList<String> getPicList() {
        return picList;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public String getGoodsInfoUrl() {
        return goodsInfoUrl;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(picList);
        parcel.writeString(goodsName);
        parcel.writeString(goodsNum);
        parcel.writeString(goodsSpec);
        parcel.writeString(goodsPrice);
        parcel.writeInt(goodsType);
        parcel.writeString(goodsInfoUrl);
    }


    public static ProductViewBean getProductViewBeanList(WelfareGoodsBean mWelfareGoodsBean,int type){
        ProductViewBean  mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsInfoUrl(UrlConstants.SHOPPING_INFO + mWelfareGoodsBean.getBenId());
        mProductViewBean.setGoodsName(mWelfareGoodsBean.getBenName());
        mProductViewBean.setGoodsNum(mWelfareGoodsBean.getBenStock()+"");
        mProductViewBean.setGoodsSpec(mWelfareGoodsBean.getBenSpec());
        mProductViewBean.setGoodsPrice(mWelfareGoodsBean.getBenPrice()+"");
        mProductViewBean.setGoodsType(type);
        //mProductViewBean.setPicList();
        return mProductViewBean;


    }

}
