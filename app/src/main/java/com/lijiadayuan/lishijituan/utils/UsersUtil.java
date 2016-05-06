package com.lijiadayuan.lishijituan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

}
