package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-09.
 * E-mail:johnyylee@163.com
 *
 * 头条
 */
public class Topics {

    private int topId;                      // 主键,自动增长
    private String topTitle;                // 头条标题
    private String topImg;                  // 头条图片
    private long topDate;                   // 头条发布日期
    private String topAuthor;               // 头条发布人
    private int topSort;                    // 头条分类
    private String topContent;              // 头条正文内容
    private int topPosition;                // 排列位置
    private int topShow;                    // 是否显示，0:不显示，1:显示。默认1

    public Topics() {
        super();
    }

    public Topics(int topId, String topTitle, String topImg, long topDate, String topAuthor, int topSort, String topContent, int topPosition, int topShow) {
        this.topId = topId;
        this.topTitle = topTitle;
        this.topImg = topImg;
        this.topDate = topDate;
        this.topAuthor = topAuthor;
        this.topSort = topSort;
        this.topContent = topContent;
        this.topPosition = topPosition;
        this.topShow = topShow;
    }

    public int getTopId() {
        return topId;
    }

    public void setTopId(int topId) {
        this.topId = topId;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public long getTopDate() {
        return topDate;
    }

    public void setTopDate(long topDate) {
        this.topDate = topDate;
    }

    public String getTopAuthor() {
        return topAuthor;
    }

    public void setTopAuthor(String topAuthor) {
        this.topAuthor = topAuthor;
    }

    public int getTopSort() {
        return topSort;
    }

    public void setTopSort(int topSort) {
        this.topSort = topSort;
    }

    public String getTopContent() {
        return topContent;
    }

    public void setTopContent(String topContent) {
        this.topContent = topContent;
    }

    public int getTopPosition() {
        return topPosition;
    }

    public void setTopPosition(int topPosition) {
        this.topPosition = topPosition;
    }

    public int getTopShow() {
        return topShow;
    }

    public void setTopShow(int topShow) {
        this.topShow = topShow;
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }
}
