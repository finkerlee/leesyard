package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.MiaoshaUtil;
import com.lijiadayuan.lishijituan.utils.MyCountdownTimer;
import com.lijiadayuan.lishijituan.utils.VerficationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifanqiao on 16/3/11.
 */
public class ForgetpasswordActivtiy extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ForgetpasswordActivitiy";
    private EditText etphone,etcode;
    private Button mBtnGetVerfication;
    private TextView tvTitle;
    // 保存验证码
    private String verificationCode = "1000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_forget_password);
        findViewById();
        initView();
        tvTitle.setText("忘了密码");
    }
    protected void findViewById(){
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_Verification).setOnClickListener(this);
        etphone= (EditText) findViewById(R.id.et_phone);
        mBtnGetVerfication = (Button) findViewById(R.id.btn_code);
        tvTitle= (TextView) findViewById(R.id.text_title);
        etcode= (EditText) findViewById(R.id.et_code);
    }
    protected void initView(){

        etphone.setOnClickListener(this);
        etcode.setOnClickListener(this);
        mBtnGetVerfication.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        String phone=etphone.getText().toString();
        switch (v.getId()) {
            case R.id.btn_Verification:
                //判断手机号是否为空 是否正确
                if (!VerficationUtil.checkMobile(this, phone)) {
                    Log.v(TAG, "phone empty");
                    return;
                }
                //判断验证码是否为空 是否正确
                if (TextUtils.isEmpty(etcode.getText())) {
                    Toast.makeText(ForgetpasswordActivtiy.this, R.string.forgetpass_code, Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "code empty");
                    return;
                } else if (!verificationCode.equals(etcode.getText().toString())) {
                    Toast.makeText(ForgetpasswordActivtiy.this, R.string.forgetpass_code_no, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent =new Intent(this,ResetpasswordActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
                break;


            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_code:
                if (VerficationUtil.checkMobile(this, etphone.getText().toString())){
                    new Thread() {
                        @Override
                        public void run() {
                            verificationCode = VerficationUtil.getVerficationCode();
                            HashMap result = VerficationUtil.get(etphone.getText().toString(), verificationCode,"80278");
                            if (VerficationUtil.OK.equals(result.get("statusCode"))) {
                                //TODO 正常
                                Log.i(TAG, "正常");
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        setCountDown();
                                        Toast.makeText(ForgetpasswordActivtiy.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Log.i(TAG, "异常");
                                Log.i(TAG, result.get("statusMsg") + "");
                                //TODO 异常
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForgetpasswordActivtiy.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }.start();
                }else{
                    Toast.makeText(ForgetpasswordActivtiy.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
            default:
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
