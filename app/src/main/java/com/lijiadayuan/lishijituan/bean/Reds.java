package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-23.
 * E-mail:johnyylee@163.com
 *
 * 封装reds表数据的类
 */
public class Reds {
    
    private String redId;                           // 主键,流水号
    private String redName;                         // 红包名称
    private int redSort;                            // 红包分类,外键
    private long redLimit;                          // 领取期限,时间戳
    private int redStock;                           // 红包剩余数量
    private double redAmount;                       // 红包金额
    private String redThumb;                         // 红包缩略图
    private String redImg;                          // 红包图片访问地址
    private String redIntro;                        // 领取说明
    private String redDetail;                       // 具体详情
    private long redDate;                           // 红包发布日期
    private int redShow;                            // 是否展示，0:不展示，1:展示。默认1
    private int redOut;                             // 是否过期，0:过期，1:未过期。默认1
    private int redVerify;                          // 是否需要上传认证材料  0：不需要 1：需要
    private int redStatus;                          // 判断当前用户是否领取过红包的标识,0:未领取,未申请; 1:已申请未审核; 2:审核通过,可领取; 3:已领取; -1:审核失败

    public Reds() {
        super();
    }

    public Reds(String redId, String redName, int redSort, long redLimit, int redStock, double redAmount, String redThumb, String redImg, String redIntro, String redDetail, long redDate, int redShow, int redOut, int redVerify, int redStatus) {
        this.redId = redId;
        this.redName = redName;
        this.redSort = redSort;
        this.redLimit = redLimit;
        this.redStock = redStock;
        this.redAmount = redAmount;
        this.redThumb = redThumb;
        this.redImg = redImg;
        this.redIntro = redIntro;
        this.redDetail = redDetail;
        this.redDate = redDate;
        this.redShow = redShow;
        this.redOut = redOut;
        this.redVerify = redVerify;
        this.redStatus = redStatus;
    }

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        this.redId = redId;
    }

    public String getRedName() {
        return redName;
    }

    public void setRedName(String redName) {
        this.redName = redName;
    }

    public int getRedSort() {
        return redSort;
    }

    public void setRedSort(int redSort) {
        this.redSort = redSort;
    }

    public long getRedLimit() {
        return redLimit;
    }

    public void setRedLimit(long redLimit) {
        this.redLimit = redLimit;
    }

    public int getRedStock() {
        return redStock;
    }

    public void setRedStock(int redStock) {
        this.redStock = redStock;
    }

    public double getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(double redAmount) {
        this.redAmount = redAmount;
    }

    public String getRedThumb() {
        return redThumb;
    }

    public void setRedThumb(String redThumb) {
        this.redThumb = redThumb;
    }

    public String getRedImg() {
        return redImg;
    }

    public void setRedImg(String redImg) {
        this.redImg = redImg;
    }

    public String getRedIntro() {
        return redIntro;
    }

    public void setRedIntro(String redIntro) {
        this.redIntro = redIntro;
    }

    public String getRedDetail() {
        return redDetail;
    }

    public void setRedDetail(String redDetail) {
        this.redDetail = redDetail;
    }

    public long getRedDate() {
        return redDate;
    }

    public void setRedDate(long redDate) {
        this.redDate = redDate;
    }

    public int getRedShow() {
        return redShow;
    }

    public void setRedShow(int redShow) {
        this.redShow = redShow;
    }

    public int getRedOut() {
        return redOut;
    }

    public void setRedOut(int redOut) {
        this.redOut = redOut;
    }

    public int getRedStatus() {
        return redStatus;
    }

    public void setRedStatus(int redStatus) {
        this.redStatus = redStatus;
    }

    public int getRedVerify() {
        return redVerify;
    }

    public void setRedVerify(int redVerify) {
        this.redVerify = redVerify;
    }
}
