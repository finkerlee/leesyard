package com.lijiadayuan.lishijituan.bean;

/**
 * Created by zhaoyi on 16/4/27.
 * 福利商品类
 */
public class WelfareGoodsBean {
    //浏览次数
    private int benClick;
    //福利商品上传时间（时间戳）
    private int benDate;
    //福利商品编号，流水号
    private String benId;
    //福利商品图片存放路径
    private String benImg;
    //福利商品介绍
    private String benIntro;
    //福利商品名称
    private String benName;
    //排列位置
    private int benPosition;
    //福利商品运费
    private int benPrice;
    //福利商品备注
    private String benRemark;
    //是否展示，0:不展示，1:展示。默认1
    private int benShow;
    //福利商品分类，外键，与福利商品分类表主键对应
    private int benSort;
    //福利商品规格
    private String benSpec;
    //福利商品库存
    private int benStock;
    //福利商品图片缩略图存放路径
    private String benThumb;

    public int getBenClick() {
        return benClick;
    }

    public int getBenDate() {
        return benDate;
    }

    public String getBenId() {
        return benId;
    }

    public String getBenImg() {
        return benImg;
    }

    public String getBenIntro() {
        return benIntro;
    }

    public String getBenName() {
        return benName;
    }

    public int getBenPosition() {
        return benPosition;
    }

    public int getBenPrice() {
        return benPrice;
    }

    public String getBenRemark() {
        return benRemark;
    }

    public int getBenShow() {
        return benShow;
    }

    public int getBenSort() {
        return benSort;
    }

    public String getBenSpec() {
        return benSpec;
    }

    public int getBenStock() {
        return benStock;
    }

    public String getBenThumb() {
        return benThumb;
    }

    public void setBenClick(int benClick) {
        this.benClick = benClick;
    }

    public void setBenDate(int benDate) {
        this.benDate = benDate;
    }

    public void setBenId(String benId) {
        this.benId = benId;
    }

    public void setBenImg(String benImg) {
        this.benImg = benImg;
    }

    public void setBenIntro(String benIntro) {
        this.benIntro = benIntro;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public void setBenPosition(int benPosition) {
        this.benPosition = benPosition;
    }

    public void setBenPrice(int benPrice) {
        this.benPrice = benPrice;
    }

    public void setBenRemark(String benRemark) {
        this.benRemark = benRemark;
    }

    public void setBenShow(int benShow) {
        this.benShow = benShow;
    }

    public void setBenSort(int benSort) {
        this.benSort = benSort;
    }

    public void setBenSpec(String benSpec) {
        this.benSpec = benSpec;
    }

    public void setBenStock(int benStock) {
        this.benStock = benStock;
    }

    public void setBenThumb(String benThumb) {
        this.benThumb = benThumb;
    }
}
