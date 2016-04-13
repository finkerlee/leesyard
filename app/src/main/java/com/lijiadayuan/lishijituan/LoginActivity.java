package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    private TextView register;
    private TextView forgetpass;
    private InputMethodManager manager;
    private EditText etUsername;                // 用户名
    private EditText etPassword;                // 密码
    private Button btnLogin;                    // 登录按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register =(TextView)findViewById(R.id.et_yonghu);
        forgetpass=(TextView)findViewById(R.id.et_pass);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "clicked");
                // 判断用户名是否为空
                if (TextUtils.isEmpty(etUsername.getText()) || null == etUsername.getText()){
                    Toast.makeText(LoginActivity.this, R.string.login_username, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "username empty");
                    return;
                }
                // 判断密码是否为空
                if (TextUtils.isEmpty(etPassword.getText()) || null == etPassword.getText()){
                    Toast.makeText(LoginActivity.this, R.string.login_password, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.LOGIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = JSON.parseObject(response);
                        String result = json.getString("response_status");
                        if ("success".equals(result)){
                            JSONObject data = json.getJSONObject("response_data");
                            if (data.isEmpty())
                                Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                            else {
                                Users user = JSON.parseObject(data.toString(), Users.class);
                                System.out.println("user: ========" + user);
                                Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userName", etUsername.getText().toString().trim());
                        params.put("userPassword", etPassword.getText().toString().trim());
                        return params;
                    }
                };
                mQueue.add(request);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.et_yonghu:
                        startActivity(new Intent(LoginActivity.this, YonghuActivity.class));
                        break;
                    case R.id.et_pass:
                        startActivity(new Intent(LoginActivity.this, ForgetpasswordActivtiy.class));
                        break;
                    default:
                        break;
                }
            }
        });
//        forgetpass=(TextView)findViewById(R.id.et_pass);
//        forgetpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.et_pass:
//                        startActivity(new Intent(LoginActivity.this, ForgetpasswordActivtiy.class));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
