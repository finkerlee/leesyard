package com.lijiadayuan.lishijituan.bean;

/**
 * Created by Lee on 2016-03-04.
 * E-mail:johnyylee@163.com
 *
 * 封装用户信息的类
 *
 */
public class Users {

    private String userId;          // 用户id，流水号
    private String userNick;        // 用户名（昵称）
    private String userName;        // 用户真实姓名
    private int userGender;         // 用户性别
    private String userPassword;    // 用户密码
    private String userPhone;       // 用户手机号
    private String userAvatar;      // 用户头像图片存放路径
    private int userIfLee;          // 用户是否通过验证为李氏人，0:未通过，1:已通过。默认0
    private int userIfShop;         // 用户是否可以购买商品，0:不可以，1:可以。默认1
    private int userIfLogin;        // 用户是否可以登录，0:不可以，1:可以。默认1
    private long userLastLogin;     // 用户上次登录时间（时间戳）
    private long userRecentLogin;   // 用户本次登录时间（时间戳）
    private long userReg;           // 用户注册时间(时间戳)
    private int userLevel;          // 用户等级，外键，与等级表主键对应
    private int userIfReceive;      // 用户是否可以领取福利


    public Users() {
        super();
    }

    public Users(String userId, String userNick, String userName, int userGender, String userPassword, String userPhone, String userAvatar, int userIfLee, int userIfShop, int userIfLogin, long userLastLogin, long userRecentLogin, long userReg, int userLevel, int userIfReceive) {
        this.userId = userId;
        this.userNick = userNick;
        this.userName = userName;
        this.userGender = userGender;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userAvatar = userAvatar;
        this.userIfLee = userIfLee;
        this.userIfShop = userIfShop;
        this.userIfLogin = userIfLogin;
        this.userLastLogin = userLastLogin;
        this.userRecentLogin = userRecentLogin;
        this.userReg = userReg;
        this.userLevel = userLevel;
        this.userIfReceive = userIfReceive;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getUserIfLee() {
        return userIfLee;
    }

    public void setUserIfLee(int userIfLee) {
        this.userIfLee = userIfLee;
    }

    public int getUserIfShop() {
        return userIfShop;
    }

    public void setUserIfShop(int userIfShop) {
        this.userIfShop = userIfShop;
    }

    public int getUserIfLogin() {
        return userIfLogin;
    }

    public void setUserIfLogin(int userIfLogin) {
        this.userIfLogin = userIfLogin;
    }

    public long getUserLastLogin() {
        return userLastLogin;
    }

    public void setUserLastLogin(long userLastLogin) {
        this.userLastLogin = userLastLogin;
    }

    public long getUserRecentLogin() {
        return userRecentLogin;
    }

    public void setUserRecentLogin(long userRecentLogin) {
        this.userRecentLogin = userRecentLogin;
    }

    public long getUserReg() {
        return userReg;
    }

    public void setUserReg(long userReg) {
        this.userReg = userReg;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserGender() {
        return userGender;
    }

    public void setUserGender(int userGender) {
        this.userGender = userGender;
    }

    public int getUserIfReceive() {
        return userIfReceive;
    }

    public void setUserIfReceive(int userIfReceive) {
        this.userIfReceive = userIfReceive;
    }
}
