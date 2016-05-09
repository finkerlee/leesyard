package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LauncherActivity extends BaseActivity{
    private Intent intent;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    //微信用到的appid
    public static final String APP_ID = "wxc4a07077153cb3a2";
    private IWXAPI weiXinApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getSharedPreferences("userInfo", 0);
        editor = mSharedPreferences.edit();
        weiXinApi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        weiXinApi.registerApp(APP_ID);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                intent = new Intent(LauncherActivity.this, HomeActivity.class);
                LauncherActivity.this.startActivity(intent);
                finish();
            }
        }, 3000); //2900 for release

        if (mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin, false)){
            // 创建请求队列
            RequestQueue mQueue = app.getRequestQueue();
            // 创建一个字符串请求
            StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.LOGIN, new Response.Listener<String>() {
                /** 重写onResponse,以实现请求响应的操作 **/
                @Override
                public void onResponse(String response) {
                    Gson mGson = new Gson();
                    JsonParser mJsonParser = new JsonParser();
                    JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                    String result = json.get("response_status").getAsString();
                    if ("success".equals(result)){
                        JsonObject data = json.get("response_data").getAsJsonObject();
                        if (data.isJsonNull()){
                            editor.clear();
                            editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
                            editor.commit();
                            //Toast.makeText(LauncherActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                        }else {
                            Users user = mGson.fromJson(data,Users.class);
                            System.out.println("user: ========" + data.toString());
                            editor.clear();
                            editor.putString(KeyConstants.UserInfoKey.userId,user.getUserId());
                            editor.putString(KeyConstants.UserInfoKey.userName, user.getUserName());
                            editor.putString(KeyConstants.UserInfoKey.userNick, user.getUserNick());
                            editor.putString(KeyConstants.UserInfoKey.userPhone, user.getUserPhone());
                            editor.putString(KeyConstants.UserInfoKey.userPassword, user.getUserPassword());
                            editor.putString(KeyConstants.UserInfoKey.userHeadImage, user.getUserAvatar());
                            editor.putBoolean(KeyConstants.UserInfoKey.userIfLee, user.getUserIfLee() == 1 ? true : false);
                            editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin, true);
                            editor.commit();
                            JPushInterface.setAliasAndTags(LauncherActivity.this, user.getUserId(),
                                    new HashSet<String>(), new TagAliasCallback() {
                                        @Override
                                        public void gotResult(int i, String s, Set<String> set) {
                                            Log.i("JPush",i + "测试");
                                            Log.i("JPush",s + "测试");
                                            //Log.i("JPush",i + "");
                                        }
                                    });
                        }
                    }else{
                        editor.clear();
                        editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
                        editor.commit();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO 判断error的code值去做相应处理
                    editor.clear();
                    editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
                    editor.commit();
                }
            }){
                /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userName",mSharedPreferences.getString(KeyConstants.UserInfoKey.userNick,""));
                    params.put("userPassword", mSharedPreferences.getString(KeyConstants.UserInfoKey.userPassword,""));
                    return params;
                }
            };
            // 将请求添加到请求队列中(即发送请求)
            mQueue.add(request);
        }
    }

}
