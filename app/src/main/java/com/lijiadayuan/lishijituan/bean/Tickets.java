package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-17.
 * E-mail:johnyylee@163.com
 *
 * 封装tickets表数据的类
 */
public class Tickets {

    private String tktId;                       // 主键,流水号
    private String tktName;                     // 卡票名称
    private int tktSort;                        // 卡票分类,外键
    private long tktLimit;                      // 领取期限,时间戳
    private int tktStock;                       // 卡票剩余数量(库存)
    private double tktAmount;                   // 卡票金额
    private String tktThumb;                    // 卡票缩略图访问地址
    private String tktImg;                      // 卡票图片访问地址
    private String tktIntro;                    // 领取说明
    private String tktDetail;                   // 具体详情
    private long tktDate;                       // 卡票发布日期
    private int tktShow;                        // 是否展示，0:不展示，1:展示。默认1
    private int tktOut;                         // 是否过期，0:过期，1:未过期。默认1
    private int tktVerify;                      // 是否需要上传认证材料
    private int tktStatus;                      // 判断当前用户是否领取过卡票的标识,0:未领取,未申请; 1:已申请未审核; 2:审核通过,可领取; 3:已领取; -1:审核失败

    public Tickets() {
        super();
    }

    public Tickets(String tktId, String tktName, int tktSort, long tktLimit, int tktStock, double tktAmount, String tktThumb, String tktImg, String tktIntro, String tktDetail, long tktDate, int tktShow, int tktOut, int tktVerify, int tktStatus) {
        this.tktId = tktId;
        this.tktName = tktName;
        this.tktSort = tktSort;
        this.tktLimit = tktLimit;
        this.tktStock = tktStock;
        this.tktAmount = tktAmount;
        this.tktThumb = tktThumb;
        this.tktImg = tktImg;
        this.tktIntro = tktIntro;
        this.tktDetail = tktDetail;
        this.tktDate = tktDate;
        this.tktShow = tktShow;
        this.tktOut = tktOut;
        this.tktVerify = tktVerify;
        this.tktStatus = tktStatus;
    }

    public String getTktId() {
        return tktId;
    }

    public void setTktId(String tktId) {
        this.tktId = tktId;
    }

    public String getTktName() {
        return tktName;
    }

    public void setTktName(String tktName) {
        this.tktName = tktName;
    }

    public int getTktSort() {
        return tktSort;
    }

    public void setTktSort(int tktSort) {
        this.tktSort = tktSort;
    }

    public long getTktLimit() {
        return tktLimit;
    }

    public void setTktLimit(long tktLimit) {
        this.tktLimit = tktLimit;
    }

    public int getTktStock() {
        return tktStock;
    }

    public void setTktStock(int tktStock) {
        this.tktStock = tktStock;
    }

    public double getTktAmount() {
        return tktAmount;
    }

    public void setTktAmount(double tktAmount) {
        this.tktAmount = tktAmount;
    }

    public String getTktIntro() {
        return tktIntro;
    }

    public void setTktIntro(String tktIntro) {
        this.tktIntro = tktIntro;
    }

    public String getTktDetail() {
        return tktDetail;
    }

    public void setTktDetail(String tktDetail) {
        this.tktDetail = tktDetail;
    }

    public long getTktDate() {
        return tktDate;
    }

    public void setTktDate(long tktDate) {
        this.tktDate = tktDate;
    }

    public int getTktShow() {
        return tktShow;
    }

    public void setTktShow(int tktShow) {
        this.tktShow = tktShow;
    }

    public int getTktOut() {
        return tktOut;
    }

    public void setTktOut(int tktOut) {
        this.tktOut = tktOut;
    }

    public String getTktImg() {
        return tktImg;
    }

    public void setTktImg(String tktImg) {
        this.tktImg = tktImg;
    }

    public int getTktStatus() {
        return tktStatus;
    }

    public void setTktStatus(int tktStatus) {
        this.tktStatus = tktStatus;
    }

    public String getTktThumb() {
        return tktThumb;
    }

    public void setTktThumb(String tktThumb) {
        this.tktThumb = tktThumb;
    }

    public int getTktVerify() {
        return tktVerify;
    }

    public void setTktVerify(int tktVerify) {
        this.tktVerify = tktVerify;
    }
}
