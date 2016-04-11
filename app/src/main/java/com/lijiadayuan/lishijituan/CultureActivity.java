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
import android.widget.Toast;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;

public class CultureActivity extends Activity implements OnClickListener{

    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.ad1, R.drawable.ad2,
            R.drawable.ad3, R.drawable.ad4, R.drawable.ad5,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);
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
        tvTitle.setText("李氏文化");
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
                if (4 == images2)
                    startActivity(new Intent(CultureActivity.this,RegistrationActivity.class));
                else if(1==images2)
                    startActivity(new Intent(CultureActivity.this,ZuxunActivity.class));
                else if(2==images2)
                    startActivity(new Intent(CultureActivity.this,CelebrityActivity.class));
                else if(0==images2)
                    startActivity(new Intent(CultureActivity.this,ZupuActivity.class));
                else if(3==images2)
                    startActivity(new Intent(CultureActivity.this,ArtActivity.class));
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
