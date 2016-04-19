package com.lijiadayuan.lishijituan;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.view.ProtocolDialog;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lifanqiao on 16/3/4.
 */

public class YonghuActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "YonghuActivity";
    private TextView text;
    private TextView textback;
    InputMethodManager manager;
    private ProtocolDialog dialog;
    private EditText etUsername;                // 用户名
    private EditText etphone;                // 手机号
    private EditText etpass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghu);
        textback=(TextView)findViewById(R.id.et_loginback);
        text=(TextView)findViewById(R.id.tx_XYuers);
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        textback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.et_loginback:
//                        startActivity(new Intent(YonghuActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:break;
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog = new ProtocolDialog(YonghuActivity.this, R.style.protocol_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

    }
    protected void initView() {
        etUsername= (EditText) findViewById(R.id.et_username);
        etphone= (EditText) findViewById(R.id.et_num);
        etpass= (EditText) findViewById(R.id.et_password);
        btnLogin= (Button) findViewById(R.id.btn_registration);
    }
    protected void findViewById() {
        etUsername.setOnClickListener(this);
        etphone.setOnClickListener(this);
        etpass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }
    //空白处隐藏软键盘
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_registration:
                Log.v(TAG, "clicked");
                // 判断用户名是否为空
                if (TextUtils.isEmpty(etUsername.getText()) || null == etUsername.getText()){
                    Toast.makeText(YonghuActivity.this, R.string.registration_username, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "username empty");
                    return;
                }
                // 判断手机号是否为空
                if (TextUtils.isEmpty(etphone.getText()) || null == etphone.getText()){
                    Toast.makeText(YonghuActivity.this, R.string.registration_phonenum, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                // 判断密码是否为空
                if (TextUtils.isEmpty(etpass.getText()) || null == etpass.getText()){
                    Toast.makeText(YonghuActivity.this, R.string.registration_password, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                // 判断密码是否为空
                if (TextUtils.isEmpty(etpass.getText()) || null == etpass.getText()){
                    Toast.makeText(YonghuActivity.this, R.string.registration_password2, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                // 判断验证码是否为空
                if (TextUtils.isEmpty(etpass.getText()) || null == etpass.getText()){
                    Toast.makeText(YonghuActivity.this, R.string.registration_code, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                // 创建请求队列
                RequestQueue mQueue = app.getRequestQueue();
                // 创建一个字符串请求
                StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.REGISTER, new Response.Listener<String>() {
                    /** 重写onResponse,以实现请求响应的操作 **/
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = JSON.parseObject(response);
                        String result = json.getString("response_status");
                        if ("success".equals(result)){
                            JSONObject data = json.getJSONObject("response_data");
                            if (data.isEmpty())
                                Toast.makeText(YonghuActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                            else {
                                Users user = JSON.parseObject(data.toString(), Users.class);
                                System.out.println("user: ========" + data.toString());
                                Toast.makeText(YonghuActivity.this, "login success", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(YonghuActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userName", etUsername.getText().toString().trim());
                        params.put("userPhone", etphone.getText().toString().trim());
                        params.put("userPassword", etpass.getText().toString().trim());
                        return params;
                    }
                };
                // 将请求添加到请求队列中(即发送请求)
                mQueue.add(request);

                break;
        }
    }
}
