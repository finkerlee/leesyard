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
    //当前商品名称
    private String goodsName;
    //当前商品数量
    private String goodsNum;
    //当前商品规格
    private String goodsSpec;
    //当前商品价格
    private String goodsPrice;
    //当前商品别名
    private String goodsOtherName;
    //当前商品的类型
    private int goodsType;
    //商品详情的url
    private String goodsInfoUrl;
    //商品展示的图片
    private String goodsPic;
    //商品的最大购买数量
    private int goodsStock;
    //商品id
    private String goodsId;


    public ProductViewBean(){

    }


    protected ProductViewBean(Parcel in) {
        picList = in.createStringArrayList();
        goodsName = in.readString();
        goodsNum = in.readString();
        goodsSpec = in.readString();
        goodsPrice = in.readString();
        goodsOtherName = in.readString();
        goodsType = in.readInt();
        goodsInfoUrl = in.readString();
        goodsPic = in.readString();
        goodsStock = in.readInt();
        goodsId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(picList);
        dest.writeString(goodsName);
        dest.writeString(goodsNum);
        dest.writeString(goodsSpec);
        dest.writeString(goodsPrice);
        dest.writeString(goodsOtherName);
        dest.writeInt(goodsType);
        dest.writeString(goodsInfoUrl);
        dest.writeString(goodsPic);
        dest.writeInt(goodsStock);
        dest.writeString(goodsId);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }


    public int getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(int goodsStock) {
        this.goodsStock = goodsStock;
    }

    public String getGoodsOtherName() {
        return goodsOtherName;
    }

    public void setGoodsOtherName(String goodsOtherName) {
        this.goodsOtherName = goodsOtherName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }


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




    public static ProductViewBean getProductViewBeanList(WelfareGoodsBean mWelfareGoodsBean,int type){
        ProductViewBean  mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsInfoUrl(UrlConstants.SHOPPING_INFO + mWelfareGoodsBean.getBenId());
        mProductViewBean.setGoodsName(mWelfareGoodsBean.getBenName());
        mProductViewBean.setGoodsNum(mWelfareGoodsBean.getBenStock() + "");
        mProductViewBean.setGoodsSpec(mWelfareGoodsBean.getBenSpec());
        mProductViewBean.setGoodsPrice(mWelfareGoodsBean.getBenPrice() + "");
        mProductViewBean.setGoodsType(type);
        //mProductViewBean.setPicList();
        return mProductViewBean;
    }

    public static ProductViewBean getProductViewBeanList(Product mProduct,int type){
        ProductViewBean  mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsInfoUrl(UrlConstants.SHOPPING_INFO + mProduct.getProId());
        mProductViewBean.setGoodsName(mProduct.getProName());
        mProductViewBean.setGoodsNum(mProduct.getProStock() + "");
        mProductViewBean.setGoodsSpec(mProduct.getProSpec());
        mProductViewBean.setGoodsPrice(mProduct.getProPrice() + "");
        mProductViewBean.setGoodsPic(mProduct.getProThumb());
        mProductViewBean.setGoodsOtherName(mProduct.getProSubtitle());
//        mProductViewBean.setPicList(mProduct.getp() + "");
        mProductViewBean.setGoodsType(type);
        mProductViewBean.setGoodsStock(mProduct.getProStock());
        mProductViewBean.setGoodsId(mProduct.getProId());
        //mProductViewBean.setPicList();
        return mProductViewBean;
    }

}
