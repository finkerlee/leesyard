package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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

public class MymessageActivity extends Activity implements View.OnClickListener{
    private TextView tvTitle;
    private ListView listView;
    private LocalUtils mLocalUtils;
    private QuickAdapter<MyMessage> mAdpter;
    private LinearLayout mLayoutNoMessage;

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
        List<MyMessage> mList = mLocalUtils.read();
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
                }
            };
            listView.setAdapter(mAdpter);
        }

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
                mLocalUtils.deleteFile();
                break;
            case R.id.read_all_message:
                break;
        }
    }
}
