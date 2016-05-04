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
    private String goodsName;//名字
    private String goodsNum;//个数
    private String goodsSpec;//领取说明
    private String goodsPrice;//价格
    private String goodsIntro;//红包领取说明
    private int goodsVerify;   // 是否需要上传认证   redVerify
    private int goodsType;
    private String goodsInfoUrl;//商品详情 webview
    private String goodsInfoIncrease;//商品浏览次数自增
    private String goodsThumb;//轮播图地址
    private long goodsLimit;//红包截止时间
    //当前商品别名
    private String goodsOtherName;
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


    public void setGoodsVerify(int goodsVerify) {
        this.goodsVerify = goodsVerify;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }



    public void setGoodsLimit(long goodsLimit) {
        this.goodsLimit = goodsLimit;
    }


    public void setGoodsInfoIncrease(String goodsInfoIncrease) {
        this.goodsInfoIncrease = goodsInfoIncrease;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public void setGoodsIntro(String goodsIntro) {
        this.goodsIntro = goodsIntro;
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

    public String getGoodsInfoIncrease() {
        return goodsInfoIncrease;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public long getGoodsLimit() {
        return goodsLimit;
    }

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public int getGoodsVerify() {
        return goodsVerify;
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
        mProductViewBean.setGoodsInfoUrl(UrlConstants.FINDSHOPPING_INFO + mProduct.getProId());
        mProductViewBean.setGoodsInfoIncrease(mProduct.getProId());
        mProductViewBean.setGoodsName(mProduct.getProName());
        mProductViewBean.setGoodsNum(mProduct.getProStock() + "");
        mProductViewBean.setGoodsSpec(mProduct.getProSpec());
        mProductViewBean.setGoodsPrice(mProduct.getProPrice() + "");
        mProductViewBean.setGoodsThumb(mProduct.getProThumb());//轮播图地址
        mProductViewBean.setGoodsPic(mProduct.getProThumb());
        mProductViewBean.setGoodsOtherName(mProduct.getProSubtitle());
//        mProductViewBean.setPicList(mProduct.getp() + "");
        mProductViewBean.setGoodsType(type);
        mProductViewBean.setGoodsStock(mProduct.getProStock());
        mProductViewBean.setGoodsId(mProduct.getProId());
        //mProductViewBean.setPicList();
        return mProductViewBean;
    }

    public static ProductViewBean getRedsViewBeanList(Reds mReds, int type) {
        ProductViewBean  mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsInfoUrl(UrlConstants.SHOPPING_INFO + mReds.getRedId());
        mProductViewBean.setGoodsName(mReds.getRedName());//名称
        mProductViewBean.setGoodsNum(mReds.getRedStock() + "");//红包数量
        mProductViewBean.setGoodsSpec(mReds.getRedDetail());//红包详情介绍
        mProductViewBean.setGoodsPrice(mReds.getRedAmount() + "");//红包金额
        mProductViewBean.setGoodsIntro(mReds.getRedIntro() + "");//红包领取说明
        mProductViewBean.setGoodsLimit(mReds.getRedLimit());//红包截止日期
        mProductViewBean.setGoodsVerify(mReds.getRedVerify());//是否需要认证
        mProductViewBean.setGoodsType(type);
        return mProductViewBean;
    }
}

