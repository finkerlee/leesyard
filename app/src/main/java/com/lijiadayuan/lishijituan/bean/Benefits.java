package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-08.
 * E-mail:johnyylee@163.com
 *
 * 封装benefits表数据的类
 */
public class Benefits {

    private String benId;                   // 福利商品编号,流水号
    private String benName;                 // 福利商品名称
    private String benSubtitle;             // 福利商品副标题
    private double benPrice;                // 福利商品运费
    private String benSpec;                 // 福利商品规格
    private int benStock;                   // 福利商品库存
    private String benThumb;                // 福利商品缩略图存放路径
    private String benImg;                  // 福利商品图片存放路径
    private String benIntro;                // 福利商品介绍
    private String benRemark;               // 福利商品备注
    private int benShow;                    // 是否展示，0:不展示，1:展示。默认1
    private int benPosition;                // 排列位置
    private long benDate;                   // 福利商品上传时间（时间戳）
    private int benClick;                   // 浏览次数
    private int benSort;                    // 福利商品分类，外键，与福利商品分类表主键对应
    private int benVerify;                  // 是否需要上传认证资料，0:不需要，1:需要。默认1
    private int baStatus;                   // 判断当前用户是否领取过福利商品的标识,0:未领取,未申请; 1:已申请未审核; 2:审核通过,可领取; 3:已领取; -1:审核失败

    public Benefits() {
        super();
    }

    public Benefits(String benId, String benName, String benSubtitle, double benPrice, String benSpec, int benStock, String benThumb, String benImg, String benIntro, String benRemark, int benShow, int benPosition, long benDate, int benClick, int benSort, int benVerify, int baStatus) {
        this.benId = benId;
        this.benName = benName;
        this.benSubtitle = benSubtitle;
        this.benPrice = benPrice;
        this.benSpec = benSpec;
        this.benStock = benStock;
        this.benThumb = benThumb;
        this.benImg = benImg;
        this.benIntro = benIntro;
        this.benRemark = benRemark;
        this.benShow = benShow;
        this.benPosition = benPosition;
        this.benDate = benDate;
        this.benClick = benClick;
        this.benSort = benSort;
        this.benVerify = benVerify;
        this.baStatus = baStatus;
    }

    public String getBenId() {
        return benId;
    }

    public void setBenId(String benId) {
        this.benId = benId;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public double getBenPrice() {
        return benPrice;
    }

    public void setBenPrice(double benPrice) {
        this.benPrice = benPrice;
    }

    public String getBenSpec() {
        return benSpec;
    }

    public void setBenSpec(String benSpec) {
        this.benSpec = benSpec;
    }

    public int getBenStock() {
        return benStock;
    }

    public void setBenStock(int benStock) {
        this.benStock = benStock;
    }

    public String getBenThumb() {
        return benThumb;
    }

    public void setBenThumb(String benThumb) {
        this.benThumb = benThumb;
    }

    public String getBenImg() {
        return benImg;
    }

    public void setBenImg(String benImg) {
        this.benImg = benImg;
    }

    public String getBenIntro() {
        return benIntro;
    }

    public void setBenIntro(String benIntro) {
        this.benIntro = benIntro;
    }

    public String getBenRemark() {
        return benRemark;
    }

    public void setBenRemark(String benRemark) {
        this.benRemark = benRemark;
    }

    public int getBenShow() {
        return benShow;
    }

    public void setBenShow(int benShow) {
        this.benShow = benShow;
    }

    public int getBenPosition() {
        return benPosition;
    }

    public void setBenPosition(int benPosition) {
        this.benPosition = benPosition;
    }

    public int getBenClick() {
        return benClick;
    }

    public void setBenClick(int benClick) {
        this.benClick = benClick;
    }

    public int getBenSort() {
        return benSort;
    }

    public void setBenSort(int benSort) {
        this.benSort = benSort;
    }

    public long getBenDate() {
        return benDate;
    }

    public void setBenDate(long benDate) {
        this.benDate = benDate;
    }

    public int getBenVerify() {
        return benVerify;
    }

    public void setBenVerify(int benVerify) {
        this.benVerify = benVerify;
    }

    public int getBaStatus() {
        return baStatus;
    }

    public void setBaStatus(int baStatus) {
        this.baStatus = baStatus;
    }

    public String getBenSubtitle() {
        return benSubtitle;
    }

    public void setBenSubtitle(String benSubtitle) {
        this.benSubtitle = benSubtitle;
    }
}
