package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

/**
 * Created by zhaoyi on 16/5/7.
 */
public class LeeActivityDetails extends BaseActivity implements View.OnClickListener{
    private TextView mTvTitle;
    private WebView mActivitesDetail;
    private Activites mActivites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lee_activites_detail);
        mActivites = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.Actvites);
        initView();
        setView();

    }

    private void setView() {
        String detailUrl = UrlConstants.ACTIVITES_DETAIL + mActivites.getActId();
        mActivitesDetail.loadUrl(detailUrl);
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.text_title);
        mActivitesDetail = (WebView) findViewById(R.id.activity_detail);
        Button mBtnJion = (Button) findViewById(R.id.i_want_receive);
        mTvTitle.setText("活动详情");
        // 判断当前用户是否已经报名的标识,申请状态，0:未报名, 1:未审核，2:审核通过，-1:审核失败，3:报名成功。默认1

        int status = mActivites.getActStatus();
        //未申请
        if (0 == status){
            mBtnJion.setText("我要报名");
            mBtnJion.setOnClickListener(this);
            mBtnJion.setBackground(getResources().getDrawable(R.drawable.rounded_button_color));
            //已申请未审核
        }else if(1 == status){
            mBtnJion.setText("审核中");
            mBtnJion.setOnClickListener(null);
            mBtnJion.setBackground(getResources().getDrawable(R.drawable.rounded_button_color_gray));
            //审核通过,可领取
        }else if (2 == status){
            mBtnJion.setText("报名成功");
            mBtnJion.setOnClickListener(null);
            mBtnJion.setBackground(getResources().getDrawable(R.drawable.rounded_button_color_gray));
            //已领取
        }else if (-1 == status){
            mBtnJion.setText("报名失败,请从新报名");
            mBtnJion.setOnClickListener(this);
            mBtnJion.setBackground(getResources().getDrawable(R.drawable.rounded_button_color));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.i_want_receive:
                if (UsersUtil.isLogin(LeeActivityDetails.this)){
                    if ("0".equals(mActivites.getActStatus())|| "-1".equals(mActivites.getActStatus())){
                        Intent intent = new Intent(LeeActivityDetails.this,RegistrationActivity.class);
                        intent.putExtra(KeyConstants.IntentPageValues.Actvites,mActivites);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(LeeActivityDetails.this, LoginActivity.class);
                    intent.putExtra(KeyConstants.IntentPageKey.ToLoginPageStyle,KeyConstants.IntentPageValues.forResult);
                    startActivityForResult(intent,LoginActivity.LOGIN);
                }
                break;
        }
    }
}
