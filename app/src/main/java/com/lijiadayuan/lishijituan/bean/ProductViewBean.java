package com.lijiadayuan.lishijituan.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.lijiadayuan.lishijituan.http.UrlConstants;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/27.
 */

public class ProductViewBean implements Parcelable{
    private ArrayList<String> picList;
    private String goodsName;//名字
    private String goodsNum;//个数
    private String goodsSpec;//商品规格
    private String goodsPrice;//价格
    private String goodsIntro;//红包领取说明
    private int goodsVerify;   // 是否需要上传认证   redVerify
    private int goodsType;
    private String goodsDetail;//// 具体详情
    private String goodsInfoUrl;//商品详情 webview
    private String goodsInfoIncrease;//商品浏览次数自增
    private String goodsThumb;//轮播图地址
    private String goodsOtherName;//当前商品别名
    private String goodsPic; //商品展示的图片
    private int goodsStock; //商品的最大购买数量
    private String goodsId; //商品id

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

    public String getGoodsDetail() {
        return goodsDetail;
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

    public void setGoodsInfoIncrease(String goodsInfoIncrease) {
        this.goodsInfoIncrease = goodsInfoIncrease;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
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

    public String getGoodsIntro() {
        return goodsIntro;
    }

    public int getGoodsVerify() {
        return goodsVerify;
    }

    /**
     *
     * 将福利商品转换成ProductViewBean
     * @param mWelfareGoodsBean
     * @param type
     * @return
     */
    public static ProductViewBean getProductViewBean(WelfareGoodsBean mWelfareGoodsBean,int type){
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
    /**
     *
     * 将普通商品转换成ProductViewBean
     * @param mProduct
     * @param type
     * @return
     */
    public static ProductViewBean getProductViewBean(Product mProduct,int type){
        ProductViewBean  mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsInfoUrl(UrlConstants.FINDSHOPPING_INFO + mProduct.getProId());
        mProductViewBean.setGoodsInfoIncrease(mProduct.getProId());
        mProductViewBean.setGoodsName(mProduct.getProName());
        mProductViewBean.setGoodsNum(mProduct.getProStock() + "");
        mProductViewBean.setGoodsSpec(mProduct.getProSpec());
        mProductViewBean.setGoodsPrice(mProduct.getProPrice() + "");
        mProductViewBean.setGoodsThumb(mProduct.getProThumb());//轮播图地址
        mProductViewBean.setGoodsOtherName(mProduct.getProSubtitle());
//        mProductViewBean.setPicList(mProduct.getp() + "");
        mProductViewBean.setGoodsType(type);
        mProductViewBean.setGoodsStock(mProduct.getProStock());
        mProductViewBean.setGoodsId(mProduct.getProId());
        //mProductViewBean.setPicList();
        return mProductViewBean;
    }




}

