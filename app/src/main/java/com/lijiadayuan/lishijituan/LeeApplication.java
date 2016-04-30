package com.lijiadayuan.lishijituan;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lijiadayuan.lishijituan.utils.LocationService;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Lee on 2016-04-13.
 * E-mail:johnyylee@163.com
 */
public class LeeApplication extends Application {

    private RequestQueue requestQueue;
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        requestQueue = Volley.newRequestQueue(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());


    }

    /**
     * 获取http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
