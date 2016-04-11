package com.lijiadayuan.lishijituan;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;

public class TicketActivity extends Activity implements OnClickListener {
    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.ad, R.drawable.ad1, R.drawable.ad2,
            R.drawable.ad3, R.drawable.ad4, R.drawable.ad5,
            R.drawable.ad, R.drawable.ad, R.drawable.ad
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        adapter = new PictureAdapter(images, this, R.layout.image);
        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.large_image);
        findViewById();
        initView();
    }
    protected void findViewById() {
        gridView = (GridView) findViewById(R.id.culture_gridView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView() {
        tvTitle.setText("卡票");
        imageback.setOnClickListener(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(TicketActivity.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
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
