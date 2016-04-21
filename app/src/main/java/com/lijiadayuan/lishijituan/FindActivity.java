package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lijiadayuan.lishijituan.adapter.PictureAdapter;

public class FindActivity extends BaseActivity {
    private GridView gridView;
    private TextView tvTitle;

    //图片ID数组
    private int[] images = new int[]{
            R.drawable.fx2, R.drawable.fx3, R.drawable.fx4,
            R.drawable.fx5
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        gridView = (GridView) findViewById(R.id.gridView);

        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.image);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(FindActivity.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("发现");
    }

}