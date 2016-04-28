package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Administrator on 2016/4/27.
 * 身份认证
 */
public class Certification {

    private int ver_id;//审核失败缘主键，自增长

    private String ver_user;//审核失申请人id，外键，与用户表主键关联

    private String ver_name;//审核失败申请人真实姓名

    private String ver_identify;//审核失败申请人身份证号

    private int ver_gender;//审核失败申请人性别，0:女，1:男

    private String  ver_img1;//审核失败申请人身份证正面照片访问地址

    private String  ver_img2;//审核失败申请人身份证反面照片访问地址

    private int ver_result;//审核结果，0:未审核，1:审核成功，-1：审核失败

    private int ver_date;//申请时间（时间戳）

    private String ver_reason ; //审核失败缘由

    public Certification(int ver_id, String ver_user, String ver_name, String ver_reason, String ver_identify, int ver_gender, String ver_img1, String ver_img2, int ver_result, int ver_date) {
        this.ver_id = ver_id;
        this.ver_user = ver_user;
        this.ver_name = ver_name;
        this.ver_reason = ver_reason;
        this.ver_identify = ver_identify;
        this.ver_gender = ver_gender;
        this.ver_img1 = ver_img1;
        this.ver_img2 = ver_img2;
        this.ver_result = ver_result;
        this.ver_date = ver_date;
    }

    public int getVer_id() {
        return ver_id;
    }

    public String getVer_user() {
        return ver_user;
    }

    public String getVer_name() {
        return ver_name;
    }

    public String getVer_identify() {
        return ver_identify;
    }

    public int getVer_gender() {
        return ver_gender;
    }

    public String getVer_img1() {
        return ver_img1;
    }

    public String getVer_img2() {
        return ver_img2;
    }

    public int getVer_result() {
        return ver_result;
    }

    public int getVer_date() {
        return ver_date;
    }

    public String getVer_reason() {
        return ver_reason;
    }

    public void setVer_id(int ver_id) {
        this.ver_id = ver_id;
    }

    public void setVer_user(String ver_user) {
        this.ver_user = ver_user;
    }

    public void setVer_name(String ver_name) {
        this.ver_name = ver_name;
    }

    public void setVer_identify(String ver_identify) {
        this.ver_identify = ver_identify;
    }

    public void setVer_gender(int ver_gender) {
        this.ver_gender = ver_gender;
    }

    public void setVer_img1(String ver_img1) {
        this.ver_img1 = ver_img1;
    }

    public void setVer_img2(String ver_img2) {
        this.ver_img2 = ver_img2;
    }

    public void setVer_result(int ver_result) {
        this.ver_result = ver_result;
    }

    public void setVer_date(int ver_date) {
        this.ver_date = ver_date;
    }

    public void setVer_reason(String ver_reason) {
        this.ver_reason = ver_reason;
    }
}
