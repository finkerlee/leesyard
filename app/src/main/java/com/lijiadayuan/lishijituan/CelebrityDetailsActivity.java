package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.http.UrlConstants;

public class CelebrityDetailsActivity extends Activity implements OnClickListener {
    private TextView Texttitle;
    private WebView Webcompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_details);
        findView();
        Intent intent = getIntent();
        setViewByData(intent.getStringExtra("celeName"), intent.getIntExtra("celeId",0));
    }
    private void findView(){
        Texttitle= (TextView) findViewById(R.id.text_title);
        Webcompany = (WebView) findViewById(R.id.celebrity_webView);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    private void setViewByData(String celeName, int celeId) {
        Webcompany.loadUrl(UrlConstants.CELEBRITIEW_DETAILS + celeId);
        Texttitle.setText(celeName);
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
