package com.lijiadayuan.lishijituan.utils;

/**
 * Created by zhaoyi on 16/4/18.
 */
public class KeyConstants {

    //本地存放的用户基本信息
    public static class UserInfoKey{
        //用户信息的实体
        public static final String userInfo = "userInfo";
        //用户id
        public static final String userId = "userId";
        //用户昵称
        public static final String userNick = "userNick";
        //用户真实姓名
        public static final String userName = "userName";
        //用户手机号
        public static final String userPhone = "userPhone";
        //用户密码
        public static final String userPassword = "userPassword";
        //用户头像
        public static final String userHeadImage = "userHeadImage";
        //用户是否通过验证为李氏人
        public static final String userIfLee = "userIfLee";
        //用户是否可以购买商品
        public static final String userIfShop = "userIfShop";
        //用户是否可以登陆
        public static final String userIfLogin = "userIfLogin";
        //用户等级
        public static final String userLevel = "userLevel";
        //用户是否可以领取福利
        public static final String userIfReceive = "userIfReceive";
        //用户是否登陆
        public static final String userIsLogin = "";

    }
    //存放跳转需要用到的一些key
    public static class IntentPageKey{
        //跳转到登陆页面时 是怎么样启动的 startActivity startActivityForResult
        public static final String ToLoginPageStyle = "LoginPageStyle";
    }

    public static class IntentPageValues{
        //跳转到登陆页面时 是怎么样启动的 startActivity startActivityForResult
        public static final String normol = "normol";
        public static final String forResult = "forResult";
    }
}
