package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;

public class RedenvelopeActivity extends Activity implements OnClickListener {

    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.red1, R.drawable.red2,
            R.drawable.red3, R.drawable.red4
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redenvelope);
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
        tvTitle.setText("红包");
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
                if (0 == images2)
                    startActivity(new Intent(RedenvelopeActivity.this,ReddenvelopebaseActivity.class));

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
