package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-11.
 * E-mail:johnyylee@163.com
 *
 * 封装首页轮播图的类
 */
public class AdvView {

    private int advId;              // 广告id
    private int advPosition;        // 广告排列位置
    private int advShow;            // 是否显示
    private String advImg;          // 广告图网络访问地址
    private String advUrl;          // 广告链接
    private String proId;           // 关联商品的id
    private String proName;         // 商品名称
    private double proPrice;        // 商品价格
    private String proSpec;         // 商品规格
    private int proStock;           // 商品库存
    private String proThumb;        // 商品缩略图网络访问地址
    private String proImg;          // 商品图片网络访问地址
    private String proIntro;        // 商品介绍
    private String proSubtitle;     // 商品副标题
    private String proRemark;       // 商品备注
    private int proShow;            // 商品是否显示
    private int proClick;           // 商品浏览数
    private int proSort;            // 商品所属分类id
    private String psName;          // 商品所属分类名称

    public AdvView() {
        super();
    }

    public AdvView(int advId, int advPosition, int advShow, String advImg, String advUrl, String proId, String proName, double proPrice, String proSpec, int proStock, String proThumb, String proImg, String proIntro, String proRemark, int proShow, int proClick, int proSort, String psName) {
        this.advId = advId;
        this.advPosition = advPosition;
        this.advShow = advShow;
        this.advImg = advImg;
        this.advUrl = advUrl;
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proSpec = proSpec;
        this.proStock = proStock;
        this.proThumb = proThumb;
        this.proImg = proImg;
        this.proIntro = proIntro;
        this.proRemark = proRemark;
        this.proShow = proShow;
        this.proClick = proClick;
        this.proSort = proSort;
        this.psName = psName;
    }

    public int getAdvId() {
        return advId;
    }

    public void setAdvId(int advId) {
        this.advId = advId;
    }

    public int getAdvPosition() {
        return advPosition;
    }

    public void setAdvPosition(int advPosition) {
        this.advPosition = advPosition;
    }

    public int getAdvShow() {
        return advShow;
    }

    public void setAdvShow(int advShow) {
        this.advShow = advShow;
    }

    public String getAdvImg() {
        return advImg;
    }

    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
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

    public int getProClick() {
        return proClick;
    }

    public void setProClick(int proClick) {
        this.proClick = proClick;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
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

    public void setProSubtitle(String proSubTitle) {
        this.proSubtitle = proSubTitle;
    }
}
