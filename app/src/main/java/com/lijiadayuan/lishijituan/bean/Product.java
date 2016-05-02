package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-08.
 * E-mail:johnyylee@163.com
 *
 * 封装products表数据的类
 */
public class Product {

    private String proId;               // 商品编号,流水号
    private String proName;             // 商品名称
    private String proSubtitle;         // 商品副标题
    private double proPrice;            // 商品价格
    private String proSpec;             // 商品规格
    private int proStock;               // 商品库存
    private String proThumb;            // 商品缩略图存放路径
    private String proImg;              // 商品图片存放路径
    private String proIntro;            // 商品介绍
    private String proRemark;           // 商品备注
    private int proShow;                // 商品是否展示,0:不展示,1:展示
    private int proPosition;            // 商品排列位置
    private long proDate;               // 商品上传日期(时间戳)
    private int proClick;               // 商品浏览次数
    private int proSort;                // 商品分类

    public Product() {
        super();
    }

    public Product(String proId, String proName, String proSubtitle, double proPrice, String proSpec, int proStock, String proThumb, String proImg, String proIntro, String proRemark, int proShow, int proPosition, long proDate, int proClick, int proSort) {
        this.proId = proId;
        this.proName = proName;
        this.proSubtitle = proSubtitle;
        this.proPrice = proPrice;
        this.proSpec = proSpec;
        this.proStock = proStock;
        this.proThumb = proThumb;
        this.proImg = proImg;
        this.proIntro = proIntro;
        this.proRemark = proRemark;
        this.proShow = proShow;
        this.proPosition = proPosition;
        this.proDate = proDate;
        this.proClick = proClick;
        this.proSort = proSort;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public double getProPrice() {
        return proPrice;
    }

    public void setProPrice(double proPrice) {
        this.proPrice = proPrice;
    }

    public String getProSpec() {
        return proSpec;
    }

    public void setProSpec(String proSpec) {
        this.proSpec = proSpec;
    }

    public int getProStock() {
        return proStock;
    }

    public void setProStock(int proStock) {
        this.proStock = proStock;
    }

    public String getProThumb() {
        return proThumb;
    }

    public void setProThumb(String proThumb) {
        this.proThumb = proThumb;
    }

    public String getProImg() {
        return proImg;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public String getProIntro() {
        return proIntro;
    }

    public void setProIntro(String proIntro) {
        this.proIntro = proIntro;
    }

    public String getProRemark() {
        return proRemark;
    }

    public void setProRemark(String proRemark) {
        this.proRemark = proRemark;
    }

    public int getProShow() {
        return proShow;
    }

    public void setProShow(int proShow) {
        this.proShow = proShow;
    }

    public int getProPosition() {
        return proPosition;
    }

    public void setProPosition(int proPosition) {
        this.proPosition = proPosition;
    }

    public long getProDate() {
        return proDate;
    }

    public void setProDate(long proDate) {
        this.proDate = proDate;
    }

    public int getProClick() {
        return proClick;
    }

    public void setProClick(int proClick) {
        this.proClick = proClick;
    }

    public int getProSort() {
        return proSort;
    }

    public void setProSort(int proSort) {
        this.proSort = proSort;
    }

    public String getProSubtitle() {
        return proSubtitle;
    }

    public void setProSubtitle(String proSubtitle) {
        this.proSubtitle = proSubtitle;
    }
}
