package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

public class EventdetailsActivity extends Activity implements OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
    private Button enroll;
    private WebView mWbDetail;
    private Activites mActivites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);
        mActivites = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.Actvites);
        findViewById();
        initView();
    }
    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        enroll= (Button) findViewById(R.id.btn_enroll);
        mWbDetail = (WebView) findViewById(R.id.activity_detail);
    }
    protected void initView() {
        tvTitle.setText("活动详情");
        imageback.setOnClickListener(this);
        enroll.setOnClickListener(this);
        mWbDetail.loadUrl("www.baidu.com");
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
