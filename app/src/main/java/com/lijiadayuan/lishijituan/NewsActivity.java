package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends Activity {
    private ListView listView;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView= (ListView) findViewById(R.id.listView);
        dataList= new ArrayList<Map<String, Object>>();
        simp_adapter=new SimpleAdapter(this,getData(), R.layout.newsdata,new String[]{"iv_date","iv_text"},new int[]{R.id.iv_date,R.id.iv_text} );
        listView.setAdapter(simp_adapter);
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
    /**
     * 初始化控件
     */
    private void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("最新消息");
    }
}
