package com.lijiadayuan.lishijituan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.MiaoshaUtil;
import com.lijiadayuan.lishijituan.utils.MyCountdownTimer;
import com.lijiadayuan.lishijituan.utils.VerficationUtil;
import com.lijiadayuan.lishijituan.view.ProtocolDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lifanqiao on 16/3/4.
 */

public class YonghuActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "YonghuActivity";
    private TextView text;
    private TextView tvTitle;
    InputMethodManager manager;
    private ProtocolDialog dialog;
    private EditText etUsername;                // 用户名
    private EditText etphone;                // 手机号
    private EditText etpass, etpass2;
    private EditText mEtverificationCode;
    private Button btnLogin;
    private Button mBtnGetVerfication;
    private CheckBox mIsArgree;
    // 保存验证吗
    private String verificationCode = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghu);
        initView();

        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        findViewById(R.id.iv_back).setOnClickListener(this);
        tvTitle.setText("注册");
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog = new ProtocolDialog(YonghuActivity.this, R.style.protocol_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

    }

    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        text = (TextView) findViewById(R.id.tx_XYuers);
        mBtnGetVerfication = (Button) findViewById(R.id.getVerfication);
        mBtnGetVerfication.setOnClickListener(this);
        etUsername = (EditText) findViewById(R.id.et_yonghu_username);
        etphone = (EditText) findViewById(R.id.et_yonghu_phonenum);
        etpass = (EditText) findViewById(R.id.et_yonghu_password);
        etpass2 = (EditText) findViewById(R.id.et_yonghu_password2 );
        btnLogin = (Button) findViewById(R.id.btn_registration);
        mIsArgree = (CheckBox) findViewById(R.id.task_info_list_button);
        btnLogin.setOnClickListener(this);
        mEtverificationCode = (EditText) findViewById(R.id.et_numpass);
    }

    //空白处隐藏软键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.getVerfication:
                if (VerficationUtil.checkMobile(this, etphone.getText().toString())){
                    new Thread() {
                        @Override
                        public void run() {
                            verificationCode = VerficationUtil.getVerficationCode();
                            HashMap result = VerficationUtil.get(etphone.getText().toString(), verificationCode,"80276");
                            if (VerficationUtil.OK.equals(result.get("statusCode"))) {
                                //TODO 正常
                                Log.i(TAG, "正常");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setCountDown();
                                        Toast.makeText(YonghuActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Log.i(TAG, "异常");
                                Log.i(TAG, result.get("statusMsg") + "");
                                //TODO 异常
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(YonghuActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            //getVerfication();

                        }
                    }.start();
                }else{
                    Toast.makeText(YonghuActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }

//
//                Log.i(TAG,"8888888888888");
//                if (!etphone.getText().toString().isEmpty()){
//
//                }

                break;

            case R.id.btn_registration:
                Log.v(TAG, "clicked");
                // 判断用户名是否为空
                if (TextUtils.isEmpty(etUsername.getText())) {
                    Toast.makeText(YonghuActivity.this, R.string.registration_username, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "username empty");
                    return;
                }

                // 检查密码是否为空
                if (TextUtils.isEmpty(etpass.getText())) {
                    Toast.makeText(YonghuActivity.this, R.string.registration_password, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password empty");
                    return;
                }
                // 判断密码是否为空
                if (TextUtils.isEmpty(etpass2.getText())) {
                    Toast.makeText(YonghuActivity.this, R.string.registration_password2, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "password2 empty");
                    return;
                }
                // j检查密码是否输入一致
                if (!etpass.getText().toString().equals(etpass2.getText().toString())) {
                    Toast.makeText(YonghuActivity.this, R.string.twice_password_no, Toast.LENGTH_SHORT).show();
                    return;
                }
                // 判断验证码是否正确
//                if (TextUtils.isEmpty(mEtverificationCode.getText())) {
//                    Toast.makeText(YonghuActivity.this, R.string.registration_code, Toast.LENGTH_SHORT).show();
//                    Log.v(TAG, "numpass empty");
//                    return;
//                } else if (!verificationCode.equals(mEtverificationCode.getText().toString())) {
//                    Toast.makeText(YonghuActivity.this, R.string.verification_code_no, Toast.LENGTH_SHORT).show();
//                    return;
//                }
                // 判断是否勾选用户协议
                if (mIsArgree.isChecked() == false) {
                    Toast.makeText(YonghuActivity.this, R.string.argree_userment, Toast.LENGTH_SHORT).show();
                    return;
                }


                    // 创建请求队列
                    RequestQueue mQueue = app.getRequestQueue();
                    // 创建一个字符串请求
                    StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.REGISTER, new Response.Listener<String>() {
                        /** 重写onResponse,以实现请求响应的操作 **/
                        @Override
                        public void onResponse(String response) {
                            JsonParser mJsonParser = new JsonParser();
                            JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                            String result = json.get("response_status").getAsString();
                            if ("success".equals(result)) {
                                if (json.get("response_data").isJsonNull()){
                                    Toast.makeText(YonghuActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }else{
                                    int data = json.get("response_data").getAsInt();
                                    if (data == 0) {
                                        Toast.makeText(YonghuActivity.this, R.string.register_failure, Toast.LENGTH_SHORT).show();
                                    } else if (data == 1) {
                                        Toast.makeText(YonghuActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(YonghuActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (data == -1) {
                                        Toast.makeText(YonghuActivity.this, "该用户名已存在", Toast.LENGTH_SHORT).show();
                                    } else if (data == -2) {
                                        Toast.makeText(YonghuActivity.this, "手机号不可用", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{

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
                            Map<String, String> params = new HashMap<>();
                            params.put("userNick", etUsername.getText().toString().trim());
                            params.put("userPhone", etphone.getText().toString().trim());
                            params.put("userPassword", etpass.getText().toString().trim());
                            return params;
                        }
                    };
//                Map<String, String> params = new HashMap<String, String>();
//                System.out.println("userNick----------" + etUsername.getText().toString().trim());
//                params.put("userName ", etUsername.getText().toString().trim());
//                params.put("userPhone", etphone.getText().toString().trim());
//                params.put("userPassword", etpass.getText().toString().trim());
//
//                JSONObject parameters = new JSONObject(params);
//
//                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlConstants.REGISTER, parameters, new Response.Listener<JSONObject>(){
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        JsonParser mJsonParser = new JsonParser();
//                        JsonObject json = mJsonParser.parse(response.toString()).getAsJsonObject();
//                        String result = json.get("response_status").getAsString();
//                        if ("success".equals(result)) {
//                            if (json.get("response_data").isJsonNull()){
//                                Toast.makeText(YonghuActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
//                            }else{
//                                int data = json.get("response_data").getAsInt();
//                                if (data == 0) {
//                                    Toast.makeText(YonghuActivity.this, R.string.register_failure, Toast.LENGTH_SHORT).show();
//                                } else if (data == 1) {
//                                    Toast.makeText(YonghuActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(YonghuActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                } else if (data == -1) {
//                                    Toast.makeText(YonghuActivity.this, "该用户名已存在", Toast.LENGTH_SHORT).show();
//                                } else if (data == -2) {
//                                    Toast.makeText(YonghuActivity.this, "手机号不可用", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }else{
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });


                    // 将请求添加到请求队列中(即发送请求)
                    mQueue.add(request);

                    break;
                }
        }

        /**
         * 设置倒计时开始
         */

    private void setCountDown() {
        mBtnGetVerfication.setEnabled(false);//设置按钮不可点击
        MiaoshaUtil localMiaoShaUtil = new MiaoshaUtil();
        localMiaoShaUtil.setCountdown(0, System.currentTimeMillis() + 121 * 1000, new MiaoshaUtil.CountDownListener() {
            @Override
            public void changed(MyCountdownTimer paramMyCountdownTimer, long residueTime, long[] threeTimePoint, int what) {
                mBtnGetVerfication.setText(residueTime / 1000 + " 秒后再次获取");
            }

            @Override
            public boolean finish(MyCountdownTimer paramMyCountdownTimer, long endRemainTime, int what) {
                mBtnGetVerfication.setEnabled(true);
                mBtnGetVerfication.setText("获取手机验证码");
                return false;
            }
        });
    }
}
