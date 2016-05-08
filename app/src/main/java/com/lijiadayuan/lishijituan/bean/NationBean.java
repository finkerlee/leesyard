package com.lijiadayuan.lishijituan.bean;

/**
 * Created by zhaoyi on 16/5/7.
 */
public class NationBean {
    private String nationName;
    private String id;

    public NationBean(String nationName, String id) {
        this.nationName = nationName;
        this.id = id;
    }

    public String getNationName() {
        return nationName;
    }

    public String getId() {
        return id;
    }
}
