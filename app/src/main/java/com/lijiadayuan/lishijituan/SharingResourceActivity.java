package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;


public class SharingResourceActivity extends Activity implements OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
    private GridView gridView;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.ad, R.drawable.ad, R.drawable.ad,
            R.drawable.ad, R.drawable.ad, R.drawable.ad,
            R.drawable.ad, R.drawable.ad, R.drawable.ad};
    private PictureAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_resource);

        adapter = new PictureAdapter(images, this, R.layout.image);
        findViewById();
        initView();
    }

    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        gridView = (GridView) findViewById(R.id.gridView);
    }

    protected void initView() {
        tvTitle.setText("共享资源");
        imageback.setOnClickListener(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(SharingResourceActivity.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
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
