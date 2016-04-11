package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class SettingActivity extends Activity implements OnClickListener {
    private TextView tvTitle, about, law,wechat;
    private ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById();
        initView();
    }

    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        about = (TextView) findViewById(R.id.iv_about);
        law = (TextView) findViewById(R.id.iv_law);
        wechat= (TextView) findViewById(R.id.iv_wechat);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }

    protected void initView() {
        tvTitle.setText("设置");
        about.setOnClickListener(this);
        law.setOnClickListener(this);
        wechat.setOnClickListener(this);
        imageback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
            case R.id.iv_law:
                startActivity(new Intent(SettingActivity.this, LawActivity.class));
                break;
            case R.id.iv_wechat:
                startActivity(new Intent(SettingActivity.this, WechatActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
