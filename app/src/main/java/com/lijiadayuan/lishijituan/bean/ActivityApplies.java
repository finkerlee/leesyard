package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-24.
 * E-mail:johnyylee@163.com
 *
 * 封装activity_applies表数据的类
 */
public class ActivityApplies {

    private long acaId;                     // 主键,自动增长
    private int acaActivity;                // 报名的活动的id，外键，与活动表主键对应
    private String acaUser;                 // 报名的用户的id，外键，与用户表主键对应
    private long acaDate;                   // 报名日期
    private String acaName;                 // 报名者姓名
    private int acaGender;                  // 报名者性别,0:女,1:男
    private int acaNation;                  // 报名者民族,外键
    private String acaPhone;                // 报名者联系方式(手机号)
    private String acaImg1;                 // 第一张图片访问地址
    private String acaImg2;                 // 第二张图片访问地址
    private String acaImg3;                 // 第三张图片访问地址
    private String acaImg4;                 // 第四张图片访问地址
    private int acaStatus;                  // 申请状态，0:为报名, 1:未审核，2:审核通过，-1:审核失败，3:报名成功。默认1
    private String acaReason;               // 审核失败原因

    public ActivityApplies() {
        super();
    }

    public ActivityApplies(long acaId, int acaActivity, String acaUser, long acaDate, String acaName, int acaGender, int acaNation, String acaPhone, String acaImg1, String acaImg2, String acaImg3, String acaImg4, int acaStatus, String acaReason) {
        this.acaId = acaId;
        this.acaActivity = acaActivity;
        this.acaUser = acaUser;
        this.acaDate = acaDate;
        this.acaName = acaName;
        this.acaGender = acaGender;
        this.acaNation = acaNation;
        this.acaPhone = acaPhone;
        this.acaImg1 = acaImg1;
        this.acaImg2 = acaImg2;
        this.acaImg3 = acaImg3;
        this.acaImg4 = acaImg4;
        this.acaStatus = acaStatus;
        this.acaReason = acaReason;
    }

    public long getAcaId() {
        return acaId;
    }

    public void setAcaId(long acaId) {
        this.acaId = acaId;
    }

    public int getAcaActivity() {
        return acaActivity;
    }

    public void setAcaActivity(int acaActivity) {
        this.acaActivity = acaActivity;
    }

    public String getAcaUser() {
        return acaUser;
    }

    public void setAcaUser(String acaUser) {
        this.acaUser = acaUser;
    }

    public long getAcaDate() {
        return acaDate;
    }

    public void setAcaDate(long acaDate) {
        this.acaDate = acaDate;
    }

    public String getAcaName() {
        return acaName;
    }

    public void setAcaName(String acaName) {
        this.acaName = acaName;
    }

    public int getAcaGender() {
        return acaGender;
    }

    public void setAcaGender(int acaGender) {
        this.acaGender = acaGender;
    }

    public int getAcaNation() {
        return acaNation;
    }

    public void setAcaNation(int acaNation) {
        this.acaNation = acaNation;
    }

    public String getAcaPhone() {
        return acaPhone;
    }

    public void setAcaPhone(String acaPhone) {
        this.acaPhone = acaPhone;
    }

    public String getAcaImg1() {
        return acaImg1;
    }

    public void setAcaImg1(String acaImg1) {
        this.acaImg1 = acaImg1;
    }

    public String getAcaImg2() {
        return acaImg2;
    }

    public void setAcaImg2(String acaImg2) {
        this.acaImg2 = acaImg2;
    }

    public String getAcaImg3() {
        return acaImg3;
    }

    public void setAcaImg3(String acaImg3) {
        this.acaImg3 = acaImg3;
    }

    public String getAcaImg4() {
        return acaImg4;
    }

    public void setAcaImg4(String acaImg4) {
        this.acaImg4 = acaImg4;
    }

    public int getAcaStatus() {
        return acaStatus;
    }

    public void setAcaStatus(int acaStatus) {
        this.acaStatus = acaStatus;
    }

    public String getAcaReason() {
        return acaReason;
    }

    public void setAcaReason(String acaReason) {
        this.acaReason = acaReason;
    }
}
