package com.lijiadayuan.lishijituan.bean;

/**
 * Created by zhaoyi on 16/4/21.
 */
public class OrderBean {
    //图片地址
    private String ImagePath;
    //商品名称
    private String shoppingName;
    //订单价格
    private String shoppingPrice;
    //商品数量
    private String shoppingNum;
    //订单状态
    private String OrderStatus;
    //商品单价
    private String shoppingUnitPrice;
    //运费
    private String freight;


    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    public void setShoppingPrice(String shoppingPrice) {
        this.shoppingPrice = shoppingPrice;
    }

    public void setShoppingNum(String shoppingNum) {
        this.shoppingNum = shoppingNum;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public void setShoppingUnitPrice(String shoppingUnitPrice) {
        this.shoppingUnitPrice = shoppingUnitPrice;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public String getShoppingPrice() {
        return shoppingPrice;
    }

    public String getShoppingNum() {
        return shoppingNum;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getShoppingUnitPrice() {
        return shoppingUnitPrice;
    }

    public String getFreight() {
        return freight;
    }
}
