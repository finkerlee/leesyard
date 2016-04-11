package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MymessageActivity extends Activity implements View.OnClickListener{
    private TextView tvTitle;
    private ImageView imageback;
    private ListView listView;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        listView= (ListView) findViewById(R.id.listView);
        dataList= new ArrayList<Map<String, Object>>();
        simp_adapter=new SimpleAdapter(this,getData(), R.layout.newsdata,new String[]{"iv_date","iv_text"},new int[]{R.id.iv_date,R.id.iv_text} );
        listView.setAdapter(simp_adapter);
        findViewById();
        initView();
    }
    public List<Map<String, Object>> getData() {
        for(int i=0;i<10;i++){
            Map<String,Object>map=new HashMap<String,Object>();
            map.put("iv_date","2016-03-17"+" "+"16:2"+i);
            map.put("iv_text",i+"元限购");
            dataList.add(map);
        }
        return dataList;
    }
    protected void findViewById(){
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView(){
        tvTitle.setText("我的消息");
        imageback.setOnClickListener(this);
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
