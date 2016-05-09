package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class MywelfareActivity extends Activity implements View.OnClickListener{
    private TextView tvTitle;
    private RadioGroup mTabButtonGroup;
    private RadioButton available, disable;
    private TabHost mTabHost;
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final String TAB_available = "MAIN_ACTIVITY";
    public static final String TAB_disable = "SEARCH_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywelfare);
        findViewById();
        initView();
    }
    protected void findViewById(){
        tvTitle = (TextView) findViewById(R.id.text_title);
        findViewById(R.id.iv_back).setOnClickListener(this);
//        mTabButtonGroup = (RadioGroup) findViewById(R.id.radio_group);
//        available = (RadioButton) findViewById(R.id.textView5);
//        disable = (RadioButton) findViewById(R.id.textView7);
    }
    protected void initView(){
        tvTitle.setText("我的福利");
//        Intent i_available = new Intent(this, MainActivity.class);
//        Intent i_disable = new Intent(this, FindActivity.class);
//        mTabHost.addTab(mTabHost.newTabSpec(TAB_available).setIndicator(TAB_available)
//                .setContent(i_available));
//        mTabHost.addTab(mTabHost.newTabSpec(TAB_disable)
//                .setIndicator(TAB_disable).setContent(i_disable));
//        mTabHost.setCurrentTabByTag(TAB_available);
//        mTabButtonGroup
//                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        switch (checkedId) {
//                            case R.id.textView5:
//                                mTabHost.setCurrentTabByTag(TAB_available);
//                                available.setTextColor(0xffea5959);
//                                disable.setTextColor(0xff2e2e2e);
//                                break;
//
//                            case R.id.textView7:
//                                mTabHost.setCurrentTabByTag(TAB_disable);
//                                available.setTextColor(0xff2e2e2e);
//                                disable.setTextColor(0xffea5959);
//                                break;
//
//                            default:
//                                break;
//                        }
//                    }
//                });
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
