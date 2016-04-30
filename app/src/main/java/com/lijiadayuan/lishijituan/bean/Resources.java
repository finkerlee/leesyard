package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-04-29.
 * E-mail:johnyylee@163.com
 *
 * 封装resources表数据的类
 */
public class Resources {
    private int resId;                          // 主键,自动增长
    private String resName;                     // 公司名称
    private String resImg;                      // 公司logo图片访问地址
    private int resShow;                        // 是否显示,0:不显示,1:显示.默认1
    private int resPosition;                    // 排列顺序
    private String resIntro;                    // 公司简介详情

    public Resources() {
        super();
    }

    public Resources(int resId, String resName, String resImg, int resShow, int resPosition, String resIntro) {
        this.resId = resId;
        this.resName = resName;
        this.resImg = resImg;
        this.resShow = resShow;
        this.resPosition = resPosition;
        this.resIntro = resIntro;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getResShow() {
        return resShow;
    }

    public void setResShow(int resShow) {
        this.resShow = resShow;
    }

    public int getResPosition() {
        return resPosition;
    }

    public void setResPosition(int resPosition) {
        this.resPosition = resPosition;
    }

    public String getResIntro() {
        return resIntro;
    }

    public void setResIntro(String resIntro) {
        this.resIntro = resIntro;
    }

    public String getResImg() {
        return resImg;
    }

    public void setResImg(String resImg) {
        this.resImg = resImg;
    }


}
