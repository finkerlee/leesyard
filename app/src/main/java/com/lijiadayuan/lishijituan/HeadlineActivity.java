package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadlineActivity extends Activity {
    private TextView tvTitle;
    private ImageView imageback;
    private ListView listView;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline);
        findViewById();
        initView();
    }
    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        listView= (ListView) findViewById(R.id.lv_headline);
    }
    protected void initView() {
        tvTitle.setText("大院头条");
        dataList= new ArrayList<Map<String, Object>>();
        simp_adapter=new SimpleAdapter(this,getData(), R.layout.aheadlinedata,new String[]{"iv_date","iv_text"},new int[]{R.id.lv_headline_date,R.id.imageView2} );
        listView.setAdapter(simp_adapter);
    }
    public List<Map<String, Object>> getData() {
        for(int i=0;i<10;i++){
            Map<String,Object>map=new HashMap<String,Object>();
            map.put("iv_date","2016-03-17"+" "+"16:2"+i);
            map.put("iv_text","李氏动态");
            dataList.add(map);
        }
        return dataList;
    }
}
