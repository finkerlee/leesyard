package com.lijiadayuan.lishijituan.http;

/**
 * Created by Lee on 2016-03-04.
 * E-mail:johnyylee@163.com
 */


public interface UrlConstants {
    //http://192.168.0.103:8080/
    //http://beijinglijiadayuan.com:8080/lees
    String BASE_URL = "http://192.168.0.103:8080/";                     // 测试地址
    //首页的数据
    String MAIN_PAGE_URL = BASE_URL + "main/index";

    /** 用户部分 **/
    String USER_BASE = BASE_URL + "user/";
    String LOGIN = USER_BASE + "login";                                 // 登录

    String REGISTER = USER_BASE + "register";                           // 注册
    String UPLOAD_AVATAR = USER_BASE + "uploadAvatar";                  // 上传头像图片
    String AVATAR = USER_BASE + "avatar";                               // 修改头像
    String MODIFY_PASSWORD = USER_BASE + "modifyPassword";              // 修改密码
    String FORGET_PASSWORD=USER_BASE+"forgetPassword";                  // 忘记密码

    //福利商品
    String WELFARE = BASE_URL + "benefit/all";

    //红包
    String RED = BASE_URL+"red/all";

    //申请领取红包
    String REDS_APPLY = BASE_URL + "redapply/";

    //提交领取红包申请
    String RED_APPLY = REDS_APPLY + "apply";
    //领取红包
    String RED_RECEIVE = REDS_APPLY + "status";
    //上传认证材料图片
    String RED_UPLOAD = REDS_APPLY + "upload";


    //卡票
    String TICKET = BASE_URL + "ticket/all";                            //查询全部卡票
    //申请卡票认证
    String TICAPPLY = BASE_URL + "ticapply/";
    String TICAPPLY_CERTIFICATION = TICAPPLY + "apply";                 //申请领取卡票
    String UPLOAD_TICAPPLY = TICAPPLY + "upload";                       //上传认证
    String RECEIVE_TICAPPLY = TICAPPLY + "receive";                     //领取卡票

    
    //发现
    String FIND = BASE_URL+"product/all";
    //搜索
    String SEARCH = BASE_URL+"product/key";
    // 地址

    String GET_ADDRESS = BASE_URL + "address/user?userId=";                          // 查询某一用户的全部收货地址


    //商品详情URL
    String SHOPPING_INFO = BASE_URL + "benefit/detail?benId=";

            //发现详情URL
    String FINDSHOPPING_INFO = BASE_URL + "product/detail?proId=";

    //增加商品浏览次数URL
    String SHOPPINGINCREASE = BASE_URL + "product/click?proId=";
    //李氏认证
    String  LEEVERIFICATION   = BASE_URL+"verify/";
    String  CERTIFICATION   = LEEVERIFICATION + "apply";                //提交认证申请
    String  UPLOAD_IDENTIFY = LEEVERIFICATION + "upload";               //上传身份证照片

    //李氏活动
    String LEEACTIVITY = BASE_URL + "activity/";
    //活动列表
    String GET_ALL_ACTIVITY = LEEACTIVITY + "all";
    //活动报名
    String SIGN_UP_ACTIVITY = BASE_URL + "actapply/apply";
    //活动详情
    String ACTIVITES_DETAIL =  LEEACTIVITY + "detail?actId=";


    //上传认证资料图片
    String UP_LOAD_DATA = BASE_URL + "actapply/upload";
    //共享资源
    String SHARING_RESOURCE = BASE_URL + "resource/all";
    //共享资源公司简介URL
    String RESOURCE_DETAIL = BASE_URL + "resource/detail?resId=";

    //
    //大院头条
    String HEAD_LINE = BASE_URL+ "topic/"+"all";
    //大院头条详情
    String HEAD_LINE_DETAILS= BASE_URL + "topic/detail?topId=";
    //商品订单
    String ORDER = BASE_URL + "po/";
    //下单
    String ORDERS = ORDER + "add";
    //添加支付／完成日期
    String UPDATA_ORDERS_STATE = ORDER + "upstatus";
    //查询某用户所有商品订单信息
    String QUERY_ALL_GOODS_INFO = ORDER + "all";
    //查询某用户某状态的所有商品订单信息
    String QUERY_ALL_GOODS_INFO_BY_USERSTATE = ORDER + "status";
    //删除订单
    String DELETE_ORDER = ORDER + "delete";

    //管理收货地址
    String ADDRESS = BASE_URL + "address/";
    String USER_ADDRESS = ADDRESS + "user";                             //查询用户收货地址
    String DELETE_ADDRESS = ADDRESS + "delete";                         //删除地址
    String MODIFY_ADDRESS = ADDRESS + "modify";                         //编辑地址
    String ADD_ADDRESS = ADDRESS + "add";                               //添加地址


    //李氏名人
    String CELEBRITIES = BASE_URL + "celebrities/";
    String ALL_CELEBRITIES = CELEBRITIES + "all";                       //查询全部名人
    //李氏名人URL详情
    String CELEBRITIEW_DETAILS = CELEBRITIES + "detail/?celeId=";


    //福利申请
    String BA = BASE_URL + "ba/";
    //福利申请图片上传
    String UP_LOAD_PIC = BA + "upload";
    //申请领取福利商品
    String APPLY = BA + "apply";
    //活动申请报名
    String ACTIVITY =BASE_URL  + "actapply/apply";




//    // 地址
//    String GET_ADDRESS = BASE_URL + "address/user";                          // 查询某一用户的全部收货地址
//    String DELETE_ADDRESS = BASE_URL + "address/delete";                    // 删除收货地址
//    String MODIFY_ADDRESS = BASE_URL + "address/modify";                    // 修改收货地址
//    String ADD_ADDRESS = BASE_URL + "address/add";                           // 添加收货地址//添加地址
}
