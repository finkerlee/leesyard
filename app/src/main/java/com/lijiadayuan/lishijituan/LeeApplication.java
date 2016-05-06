package com.lijiadayuan.lishijituan;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lijiadayuan.lishijituan.utils.LocationService;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Lee on 2016-04-13.
 * E-mail:johnyylee@163.com
 */
public class LeeApplication extends Application {

    private RequestQueue requestQueue;
    public LocationService locationService;
    public Vibrator mVibrator;

    public static String newMessagePath = "";
    public static String myMessagePath = "";

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

        //创建一个newMessage的本地文件
        File fileNewMsg = new File(getFilesDir(),"newMessgae");
        if (!fileNewMsg.exists()){
            try {
                fileNewMsg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        newMessagePath = fileNewMsg.getPath();

        //创建一个myMessage的本地文件
        File fileMyMsy = new File(getFilesDir(),"MyMessgae");
        if (!fileMyMsy.exists()){
            try {
                fileMyMsy.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myMessagePath = fileMyMsy.getPath();


        //启动一个service去解析本地XML的数据
        Intent intent=new Intent(this,LeeService.class);
        startService(intent);


    }


    /**
     * 获取http请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
