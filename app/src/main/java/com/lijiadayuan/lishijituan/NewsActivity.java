package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.NewMessage;
import com.lijiadayuan.lishijituan.utils.LocalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends Activity {
    private ListView listView;
    private List<NewMessage> mList;
    private QuickAdapter<NewMessage> mAdpter;
    private LinearLayout mLayoutNoMessage;
    private LocalUtils mLocalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView= (ListView) findViewById(R.id.listView);
        mLayoutNoMessage = (LinearLayout) findViewById(R.id.no_message);
        mLocalUtils = new LocalUtils(LeeApplication.newMessagePath);
        mList = (List<NewMessage>) mLocalUtils.read();
        Log.i("main",mList.size()+"");
        if (mList.size() == 0){
            mLayoutNoMessage.setVisibility(View.VISIBLE);
        }else{
            mLayoutNoMessage.setVisibility(View.GONE);
            mAdpter = new QuickAdapter<NewMessage>(NewsActivity.this,R.layout.newsdata,mList) {
                @Override
                protected void convert(BaseAdapterHelper helper, NewMessage item) {
                    helper.setText(R.id.tv_title,item.getTitle());
                    helper.setText(R.id.tv_time,item.getTime());
                    helper.setText(R.id.tv_content,item.getContent());
                    helper.setBackgroundColor(R.id.layout,item.getSee()? Color.parseColor("#ffffff"):Color.parseColor("#000000"));
                }
            };
            listView.setAdapter(mAdpter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mList.get(i).setSee(true);
                mAdpter.clear();
                mAdpter.addAll(mList);
                mAdpter.notifyDataSetChanged();
                mLocalUtils.deleteFile();
                mLocalUtils.put(mList);
            }
        });
    }

}
