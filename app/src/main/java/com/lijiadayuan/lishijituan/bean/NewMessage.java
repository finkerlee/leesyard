package com.lijiadayuan.lishijituan.bean;

import java.io.Serializable;

/**
 * Created by zhaoyi on 16/5/5.
 */
public class NewMessage implements Serializable{
    private String title;
    private String time;
    private String content;
    private Boolean isSee;

    public void setSee(Boolean see) {
        isSee = see;
    }

    public Boolean getSee() {
        return isSee;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
