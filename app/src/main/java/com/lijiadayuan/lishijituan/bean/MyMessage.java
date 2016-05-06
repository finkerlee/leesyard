package com.lijiadayuan.lishijituan.bean;

import java.io.Serializable;

/**
 * Created by zhaoyi on 16/5/5.
 */
public class MyMessage implements Serializable{
    private String time;
    private String content;


    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
