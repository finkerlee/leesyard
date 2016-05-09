package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SettingActivity extends BaseActivity implements OnClickListener {
    private TextView tvTitle, about, law,wechat;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSp = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
        initView();
    }


    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        about = (TextView) findViewById(R.id.iv_about);
        //law = (TextView) findViewById(R.id.iv_law);
        wechat= (TextView) findViewById(R.id.iv_wechat);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.cancle).setOnClickListener(this);

        tvTitle.setText("设置");
        about.setOnClickListener(this);
        //law.setOnClickListener(this);
        wechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
//            case R.id.iv_law:
//                startActivity(new Intent(SettingActivity.this, LawActivity.class));
//                break;
            case R.id.iv_wechat:
                startActivity(new Intent(SettingActivity.this, WechatActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.cancle:
                SharedPreferences.Editor mEditor = mSp.edit();
                mEditor.clear();
                mEditor.commit();
                Toast.makeText(SettingActivity.this,"注销成功",Toast.LENGTH_LONG).show();
                break;
        }
    }

}
