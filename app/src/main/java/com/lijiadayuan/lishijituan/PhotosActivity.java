package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.view.PhotosDialog;

public class PhotosActivity extends Activity implements OnClickListener {
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        findViewById();
        initView();
    }
    protected void findViewById() {

        text = (TextView) findViewById(R.id.iv_cancel);
    }
    protected void initView() {
        text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
