package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.lijiadayuan.lishijituan.http.UrlConstants;

public class HeadlineDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView Texttitle;
    private WebView Webcompany;
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline_details);
        findView();
        initView();
        Intent intent=getIntent();
        setViewByData(intent.getStringExtra("topTitle"),intent.getIntExtra("topId",0));
    }
    private void findView(){
        Texttitle= (TextView) findViewById(R.id.text_title);
        Webcompany = (WebView) findViewById(R.id.web_headline);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }
    private void initView(){
        iv_back.setOnClickListener(this);
    }

    private void setViewByData(String topTitle, int topId) {
        Webcompany.loadUrl(UrlConstants.HEAD_LINE_DETAILS + topId);
        Texttitle.setText(topTitle);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
