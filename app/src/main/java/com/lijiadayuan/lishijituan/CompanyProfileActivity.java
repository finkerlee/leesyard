package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.http.UrlConstants;

public class CompanyProfileActivity extends Activity implements OnClickListener{
    private TextView Texttitle;
    private WebView Webcompany;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        findView();
        initView();
        Intent intent = getIntent();
        setViewByData(intent.getStringExtra("resName"), intent.getIntExtra("resId", 0));
    }
    protected void findView(){
        Texttitle= (TextView) findViewById(R.id.text_title);
        Webcompany = (WebView) findViewById(R.id.WebView);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView(){
        iv_back.setOnClickListener(this);
    }

    private void setViewByData(String resName, int resId) {
        Webcompany.loadUrl(UrlConstants.RESOURCE_DETAIL + resId);
        Texttitle.setText(resName);
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
