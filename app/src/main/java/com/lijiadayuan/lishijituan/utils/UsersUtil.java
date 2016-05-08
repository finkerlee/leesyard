package com.lijiadayuan.lishijituan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.lijiadayuan.service.XmlParserHandler;

/**
 * Created by zhaoyi on 16/5/4.
 */
public class UsersUtil {
    /**
     * 是否认证性李
     * @param mContext
     * @return
     */
    public static boolean isLee(Context mContext){
        SharedPreferences mSp = mContext.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        return mSp.getBoolean(KeyConstants.UserInfoKey.userIfLee,false);
    }

    /**
     * 获取用户id
     * @param mContext
     * @return
     */
    public static String getUserId(Context mContext){
        SharedPreferences mSp = mContext.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        return mSp.getString(KeyConstants.UserInfoKey.userId,"");
    }

    /**
     * 判断当前用户是否登陆
     */
    public static Boolean isLogin(Context mContext){
        SharedPreferences mSp = mContext.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        return mSp.getBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
    }

    /**
     * 通过区id获取省市区名称并返回
     *
     * @param areaId
     * @return
     */
    public static String getPCA(String areaId) {
        int province = Integer.parseInt(areaId.substring(0, 2)) - 1;
        int city = Integer.parseInt(areaId.substring(2, 4)) - 1;
        int area = Integer.parseInt(areaId.substring(4, 6)) - 1;

        return XmlParserHandler.getInstance().getDataList().get(province).getName() +
                XmlParserHandler.getInstance().getDataList().get(province).getCityList().get(city).getName() +
                XmlParserHandler.getInstance().getDataList().get(province).getCityList().get(city).getDistrictList().get(area).getName();
    }

}
