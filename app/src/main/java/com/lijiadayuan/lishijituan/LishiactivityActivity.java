package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;

public class LishiactivityActivity extends Activity implements OnClickListener {
    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.activity1, R.drawable.activity2,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishiactivity);
        adapter = new PictureAdapter(images, this, R.layout.image_ativity);
        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.image_ativity);
        findViewById();
        initView();
    }
    protected void findViewById() {
        gridView = (GridView) findViewById(R.id.culture_gridView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView() {
        tvTitle.setText("李氏活动");
        imageback.setOnClickListener(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> images0, View images1, int images2, long images3) {
//                images0.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(CultureActivity.this,RegistrationActivity.class));
//                    }
//                });
                if (1 == images2)
                    startActivity(new Intent(LishiactivityActivity.this, EventdetailsActivity.class));
                else if (0 == images2)
                    startActivity(new Intent(LishiactivityActivity.this, EventdetailsActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
