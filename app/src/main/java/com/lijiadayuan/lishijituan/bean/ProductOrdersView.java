package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-04-08.
 * E-mail:johnyylee@163.com
 *
 * 封装product_orders_view视图数据的类
 */
public class ProductOrdersView {

    private String poId;
    private long poDate;
    private int poStatus;
    private String poExpress;
    private String poExnum;
    private long poPay;
    private long poShip;
    private long poFinish;
    private String proId;
    private String proName;
    private double proPrice;
    private String proSpec;
    private int proStock;
    private String proThumb;
    private String proImg;
    private String proIntro;
    private String proRemark;
    private long proDate;
    private int proClick;
    private String psName;
    private String addId;
    private String addName;
    private String addPhone;
    private String addProvince;
    private String addCity;
    private String addArea;
    private String addDetail;
    private int addShopped;
    private int poCount;
    private double poAmount;

    public ProductOrdersView() {
        super();
    }

    public ProductOrdersView(String poId, long poDate, int poStatus, String poExpress, String poExnum, long poPay, long poShip, long poFinish, String proId, String proName, double proPrice, String proSpec, int proStock, String proThumb, String proImg, String proIntro, String proRemark, long proDate, int proClick, String psName, String addId, String addName, String addPhone, String addProvince, String addCity, String addArea, String addDetail, int addShopped, int poCount, double poAmount) {
        this.poId = poId;
        this.poDate = poDate;
        this.poStatus = poStatus;
        this.poExpress = poExpress;
        this.poExnum = poExnum;
        this.poPay = poPay;
        this.poShip = poShip;
        this.poFinish = poFinish;
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proSpec = proSpec;
        this.proStock = proStock;
        this.proThumb = proThumb;
        this.proImg = proImg;
        this.proIntro = proIntro;
        this.proRemark = proRemark;
        this.proDate = proDate;
        this.proClick = proClick;
        this.psName = psName;
        this.addId = addId;
        this.addName = addName;
        this.addPhone = addPhone;
        this.addProvince = addProvince;
        this.addCity = addCity;
        this.addArea = addArea;
        this.addDetail = addDetail;
        this.addShopped = addShopped;
        this.poCount = poCount;
        this.poAmount = poAmount;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public long getPoDate() {
        return poDate;
    }

    public void setPoDate(long poDate) {
        this.poDate = poDate;
    }

    public int getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(int poStatus) {
        this.poStatus = poStatus;
    }

    public String getPoExpress() {
        return poExpress;
    }

    public void setPoExpress(String poExpress) {
        this.poExpress = poExpress;
    }

    public String getPoExnum() {
        return poExnum;
    }

    public void setPoExnum(String poExnum) {
        this.poExnum = poExnum;
    }

    public long getPoPay() {
        return poPay;
    }

    public void setPoPay(long poPay) {
        this.poPay = poPay;
    }

    public long getPoShip() {
        return poShip;
    }

    public void setPoShip(long poShip) {
        this.poShip = poShip;
    }

    public long getPoFinish() {
        return poFinish;
    }

    public void setPoFinish(long poFinish) {
        this.poFinish = poFinish;
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

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddPhone() {
        return addPhone;
    }

    public void setAddPhone(String addPhone) {
        this.addPhone = addPhone;
    }

    public String getAddProvince() {
        return addProvince;
    }

    public void setAddProvince(String addProvince) {
        this.addProvince = addProvince;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddArea() {
        return addArea;
    }

    public void setAddArea(String addArea) {
        this.addArea = addArea;
    }

    public String getAddDetail() {
        return addDetail;
    }

    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail;
    }

    public int getAddShopped() {
        return addShopped;
    }

    public void setAddShopped(int addShopped) {
        this.addShopped = addShopped;
    }

    public int getPoCount() {
        return poCount;
    }

    public void setPoCount(int poCount) {
        this.poCount = poCount;
    }

    public double getPoAmount() {
        return poAmount;
    }

    public void setPoAmount(double poAmount) {
        this.poAmount = poAmount;
    }
}
