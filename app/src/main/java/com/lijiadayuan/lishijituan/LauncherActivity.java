package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends BaseActivity{
    private Intent intent;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        editor = mSharedPreferences.edit();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                intent = new Intent(LauncherActivity.this, HomeActivity.class);
                LauncherActivity.this.startActivity(intent);
                LauncherActivity.this.finish();
            }
        }, 2900); //2900 for release

        if (getSharedPreferences("userInfo",Activity.MODE_PRIVATE).getBoolean(KeyConstants.UserInfoKey.userIsLogin,false)){
            // 创建请求队列
            RequestQueue mQueue = app.getRequestQueue();
            // 创建一个字符串请求
            StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.LOGIN, new Response.Listener<String>() {
                /** 重写onResponse,以实现请求响应的操作 **/
                @Override
                public void onResponse(String response) {
                    JSONObject json = JSON.parseObject(response);
                    String result = json.getString("response_status");
                    if ("success".equals(result)){
                        JSONObject data = json.getJSONObject("response_data");
                        if (data.isEmpty()){
                            editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
                            editor.clear();
                            //Toast.makeText(LauncherActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                        }else {
                            Users user = JSON.parseObject(data.toString(), Users.class);
                            System.out.println("user: ========" + data.toString());
                            editor.clear();
                            editor.putString(KeyConstants.UserInfoKey.userId, user.getUserId());
                            editor.putString(KeyConstants.UserInfoKey.userName, user.getUserName());
                            editor.putString(KeyConstants.UserInfoKey.userNick, user.getUserNick());
                            editor.putString(KeyConstants.UserInfoKey.userPhone, user.getUserPhone());
                            editor.putString(KeyConstants.UserInfoKey.userPassword,user.getUserPassword());
                            editor.putString(KeyConstants.UserInfoKey.userHeadImage, user.getUserNick());
                            editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin,true);
                            editor.commit();
                        }
                    }else{
                        editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
                        editor.clear();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO 判断error的code值去做相应处理
                    editor.putBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
                    editor.clear();
                }
            }){
                /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userName",mSharedPreferences.getString(KeyConstants.UserInfoKey.userPhone,""));
                    params.put("userPassword", mSharedPreferences.getString(KeyConstants.UserInfoKey.userName,""));
                    return params;
                }
            };
            // 将请求添加到请求队列中(即发送请求)
            mQueue.add(request);
        }
    }

}
