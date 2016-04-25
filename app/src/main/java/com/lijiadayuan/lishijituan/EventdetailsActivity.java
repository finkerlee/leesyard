package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventdetailsActivity extends Activity implements OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
    private Button enroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);
        findViewById();
        initView();
    }
    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        enroll= (Button) findViewById(R.id.btn_enroll);
    }
    protected void initView() {
        tvTitle.setText("活动详情");
        imageback.setOnClickListener(this);
        enroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_enroll:
                startActivity(new Intent(this,RegistrationActivity.class));
            default:
                break;
        }
    }
}
