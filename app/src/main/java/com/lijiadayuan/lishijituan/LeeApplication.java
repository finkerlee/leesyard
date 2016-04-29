package com.lijiadayuan.lishijituan;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Lee on 2016-04-13.
 * E-mail:johnyylee@163.com
 */
public class LeeApplication extends Application {

    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        requestQueue = Volley.newRequestQueue(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 获取http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
