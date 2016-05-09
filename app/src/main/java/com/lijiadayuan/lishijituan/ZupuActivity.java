package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ZupuActivity extends Activity implements View.OnClickListener{
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zupu);
        findViewById();
        initView();
    }
    protected void findViewById(){
        tvTitle = (TextView) findViewById(R.id.text_title);

        findViewById(R.id.iv_back).setOnClickListener(this);
    }
    protected void initView(){
        tvTitle.setText("李氏祖谱");

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
