package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById();
        initView();
    }
    protected void findViewById(){
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView(){
        tvTitle.setText("关于我们le");
        imageback.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;
            }
    }
}
