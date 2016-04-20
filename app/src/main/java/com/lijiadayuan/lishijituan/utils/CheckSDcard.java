package com.lijiadayuan.lishijituan.utils;

import android.os.Environment;

/**
 * Created by zhaoyi on 16/4/20.
 */
public class CheckSDcard {
    /**
     * 检查是否存在SDCard
     * @return
     */
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
