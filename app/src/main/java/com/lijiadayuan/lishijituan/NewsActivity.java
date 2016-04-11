package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsActivity extends Activity {
    private ListView listView;
    private SimpleAdapter simp_adapter;
    private List<Map<String, Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView= (ListView) findViewById(R.id.listView);
        dataList= new ArrayList<Map<String, Object>>();
        simp_adapter=new SimpleAdapter(this,getData(), R.layout.newsdata,new String[]{"iv_date","iv_text"},new int[]{R.id.iv_date,R.id.iv_text} );
        listView.setAdapter(simp_adapter);
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
}
