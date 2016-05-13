package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.MyMessage;
import com.lijiadayuan.lishijituan.utils.LocalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MymessageActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvTitle;
    private ListView listView;
    private LocalUtils mLocalUtils;
    private QuickAdapter<MyMessage> mAdpter;
    private LinearLayout mLayoutNoMessage;
    private List<MyMessage> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        initView();
        initData();
//        dataList= new ArrayList<Map<String, Object>>();
//        simp_adapter=new SimpleAdapter(this,getData(), R.layout.newsdata,new String[]{"iv_date","iv_text"},new int[]{R.id.iv_date,R.id.iv_text} );
//        listView.setAdapter(simp_adapter);
//        findViewById();
//        initView();
    }

    private void initData() {
        mLocalUtils = new LocalUtils(LeeApplication.myMessagePath);
        mList = mLocalUtils.read();
        if (mList.size() == 0){
            mLayoutNoMessage.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            findViewById(R.id.layout_btn).setVisibility(View.GONE);
        }else{
            findViewById(R.id.layout_btn).setVisibility(View.VISIBLE);
            mLayoutNoMessage.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            mAdpter = new QuickAdapter<MyMessage>(MymessageActivity.this,R.layout.item_myessage,mList) {
                @Override
                protected void convert(BaseAdapterHelper helper, MyMessage item) {
                    helper.setText(R.id.tv_time,item.getTime());
                    helper.setText(R.id.tv_content,item.getContent());
                    helper.setTextColor(R.id.tv_time,item.getmIsRead()? Color.parseColor("#b9b9b9"):Color.parseColor("#000000"));
                    helper.setTextColor(R.id.tv_content,item.getmIsRead()? Color.parseColor("#b9b9b9"):Color.parseColor("#000000"));
                }
            };
            listView.setAdapter(mAdpter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mList.get(i).setmIsRead(true);
                mAdpter.clear();
                mAdpter.addAll(mList);
                mAdpter.notifyDataSetChanged();
                updataMessage();
            }
        });

    }
    protected void initView(){
        tvTitle = (TextView) findViewById(R.id.text_title);

        findViewById(R.id.iv_back).setOnClickListener(this);

        listView= (ListView) findViewById(R.id.listView);
        findViewById(R.id.clear_message).setOnClickListener(this);
        findViewById(R.id.read_all_message).setOnClickListener(this);
        mLayoutNoMessage = (LinearLayout) findViewById(R.id.layout_no_message);
        tvTitle.setText("我的消息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.clear_message:
                mList.clear();
                mAdpter.clear();
                mAdpter.notifyDataSetChanged();
                listView.setVisibility(View.GONE);
                mLayoutNoMessage.setVisibility(View.VISIBLE);
                findViewById(R.id.layout_btn).setVisibility(View.GONE);
                clearMessage();
                break;
            case R.id.read_all_message:
                for (MyMessage mMyMessage : mList ){
                    mMyMessage.setmIsRead(true);
                }
                mAdpter.clear();
                mAdpter.addAll(mList);
                mAdpter.notifyDataSetChanged();
                updataMessage();
                break;
        }
    }
    /**
     * 更新数据
     */
    private void updataMessage(){
        new Thread(){
            @Override
            public void run() {
                mLocalUtils.deleteFile();
                mLocalUtils.put(mList);
            }
        }.start();
    }
    /**
     * 删除存放消息的文件夹
     */
    private void clearMessage(){
        new Thread(){
            @Override
            public void run() {
                mLocalUtils.deleteFile();
            }
        }.start();
    }
}
