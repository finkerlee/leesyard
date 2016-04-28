package com.lijiadayuan.lishijituan.http;

/**
 * Created by Lee on 2016-03-04.
 * E-mail:johnyylee@163.com
 */
public interface UrlConstants {

    String BASE_URL = "http://192.168.0.101:8080/";                     // 测试地址

    /** 用户部分 **/
    String USER_BASE = BASE_URL + "user/";
    String LOGIN = USER_BASE + "login";                                 // 登录

    String REGISTER = USER_BASE + "register";                           // 注册
    String UPLOAD_AVATAR = USER_BASE + "uploadAvatar";                  // 上传头像图片
    String AVATAR = USER_BASE + "avatar";                               // 修改头像
    String MODIFY_PASSWORD = USER_BASE + "modifyPassword";              // 修改/忘记密码

    //福利商品
    String WELFARE = BASE_URL + "benefit/all";
    //商品详情URL
    String SHOPPING_INFO = BASE_URL + "benefit/detail?benId=";
    //李氏认证
    String  LEEVERIFICATION   = BASE_URL+"verify/";
    String  CERTIFICATION   = LEEVERIFICATION + "apply";                //提交认证申请
    String  UPLOAD_IDENTIFY = LEEVERIFICATION + "upload";               //上传身份证照片

    //李氏活动
    String LEEACTIVITY = BASE_URL + "activity/";
    String GET_ALL_ACTIVITY = LEEACTIVITY + "all";

}
