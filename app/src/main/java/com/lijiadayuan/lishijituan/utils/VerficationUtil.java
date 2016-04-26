package com.lijiadayuan.lishijituan.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaoyi on 16/4/26.
 */
public class VerficationUtil {
    private static final String SID = "aaf98f895335f2be01533f448578103b";
    private static final String TOKEN = "028b54832c154c7aa55f36581d41d78c";
    private static final String APPID = "aaf98f895335f2be01533f58e5bf1084";
    public static final String OK = "000000";
    private static final String TAG = "VerficationUtil";


    /**
     * 获取短信验证码
     * @param phoneNumber  手机号
     * @return
     */
    public static HashMap get(String phoneNumber,String code){
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        HashMap result = null;
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount(SID, TOKEN);
        restAPI.setAppId(APPID);
        result = restAPI.sendTemplateSMS(phoneNumber, "80276", new String[]{code, "2"});
        restAPI = null;
        Log.i("YonghuActivity", result.toString());
        return result;
    }

    /**
     * 检验手机号是否正确
     */
    public static boolean checkMobile(Context mContext,String phonenumber) {
        Boolean isCheckSuccess = true;
        if (TextUtils.isEmpty(phonenumber)) {
            isCheckSuccess = false;
            Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }else {
            Pattern pattern = Pattern.compile("^1\\d{10}");
            Matcher matcher = pattern.matcher(phonenumber);
            if (!matcher.matches()) {
                isCheckSuccess = false;
                Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            }
        }
        return isCheckSuccess;
    }

    /**
     * 获得一个4位随机数
     */
    public static String getVerficationCode(){
        Random random = new Random();
        int s = random.nextInt(9999)%(9999-1000+1)+1000;

        return s+"";
    }
}
