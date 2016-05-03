package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-04-27.
 * E-mail:johnyylee@163.com
 *
 * 封装celebrities表数据的类
 */
public class Celebrities {

    private int celeId;                             // 主键,自动增长
    private int celePosition;                       // 排列位置
    private int celeShow;                           // 是否显示
    private String celeName;                        // 名人姓名
    private String celeTitle;                       // 名人介绍标题
    private String celeIntro;                       // 名人介绍
    private String celeImg;                         // 名人缩略图访问地址

    public Celebrities() {
        super();
    }

    public Celebrities(int celeId, int celePosition, int celeShow, String celeName, String celeTitle, String celeIntro, String celeImg) {
        this.celeId = celeId;
        this.celePosition = celePosition;
        this.celeShow = celeShow;
        this.celeName = celeName;
        this.celeTitle = celeTitle;
        this.celeIntro = celeIntro;
        this.celeImg = celeImg;
    }

    public int getCeleId() {
        return celeId;
    }

    public void setCeleId(int celeId) {
        this.celeId = celeId;
    }

    public String getCeleName() {
        return celeName;
    }

    public void setCeleName(String celeName) {
        this.celeName = celeName;
    }

    public String getCeleTitle() {
        return celeTitle;
    }

    public void setCeleTitle(String celeTitle) {
        this.celeTitle = celeTitle;
    }

    public String getCeleIntro() {
        return celeIntro;
    }

    public void setCeleIntro(String celeIntro) {
        this.celeIntro = celeIntro;
    }

    public int getCelePosition() {
        return celePosition;
    }

    public void setCelePosition(int celePosition) {
        this.celePosition = celePosition;
    }

    public int getCeleShow() {
        return celeShow;
    }

    public void setCeleShow(int celeShow) {
        this.celeShow = celeShow;
    }

    public String getCeleImg() {
        return celeImg;
    }

    public void setCeleImg(String celeImg) {
        this.celeImg = celeImg;
    }
}
