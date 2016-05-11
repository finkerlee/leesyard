package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.NewMessage;
import com.lijiadayuan.lishijituan.utils.LocalUtils;
import com.lijiadayuan.lishijituan.view.AddressDialog;

import java.util.List;
import java.util.Map;

public class NewsActivity extends BaseActivity implements View.OnClickListener{
    private ListView listView;
    private TextView tvTitle;
    private List<NewMessage> mList;
    private QuickAdapter<NewMessage> mAdpter;
    private LinearLayout mLayoutNoMessage;
    private LocalUtils mLocalUtils;
    private AddressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();

        mLayoutNoMessage = (LinearLayout) findViewById(R.id.no_message);
        mLocalUtils = new LocalUtils(LeeApplication.newMessagePath);
        mList = (List<NewMessage>) mLocalUtils.read();
        Log.i("main", mList.size() + "");
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
    /**
     * 初始化控件
     */
    private void initView() {
        listView= (ListView) findViewById(R.id.listView);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("最新消息");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_address:
                dialog = new AddressDialog(NewsActivity.this, R.style.protocol_dialog,HomeActivity.mCurrentAddress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case R.id.iv_messsage:
                Intent mIntent = new Intent(NewsActivity.this,MymessageActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
