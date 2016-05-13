package com.lijiadayuan.lishijituan;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.ImageAdapter;
import com.lijiadayuan.lishijituan.bean.Reds;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReddenvelopebaseActivity extends BaseActivity implements OnClickListener {

    private static final int LOGIN = 100;
    private static final int RENZHENG = 101;

    private Button bt_reds_receive;//申请领取

    private TextView reds_name;//名字
    private TextView  reds_price;//红包金额
    private TextView reds_num;//红包剩余个数
    private TextView reds_spec;//介绍
    private TextView res_get_conditions1,res_get_conditions2,res_get_conditions3,res_get_conditions4,res_get_conditions5,res_get_conditions6;//l领取说明

    private TextView reds_AsOfTime;//截止时间

    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();

    private Reds mReds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddenvelopebase);


        //红包信息
        mReds = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);

        imageUrlList.add(mReds.getRedImg());
        findViewById();
        initBanner(imageUrlList);

        //将数据加载到视图
        setViewByData();

    }

    private void setViewByData() {
        reds_name.setText(mReds.getRedName());
        reds_price.setText("¥"+mReds.getRedAmount());
        reds_num.setText(mReds.getRedStock()+"");
        CharSequence charSequence = Html.fromHtml(mReds.getRedIntro());
        reds_spec.setText(charSequence);
        reds_spec.setMovementMethod(LinkMovementMethod.getInstance());
        int status = mReds.getRedStatus();
        //未申请
        if (0 == status){
            bt_reds_receive.setText("申请领取");
            bt_reds_receive.setOnClickListener(this);
            bt_reds_receive.setBackground(getResources().getDrawable(R.drawable.rounded_button_color));
        //已申请未审核
        }else if(1 == status){
            bt_reds_receive.setText("审核中");
            bt_reds_receive.setOnClickListener(this);
            bt_reds_receive.setBackground(getResources().getDrawable(R.drawable.rounded_button_color_gray));
        //审核通过,可领取
        }else if (2 == status){
            bt_reds_receive.setText("领取");
            bt_reds_receive.setOnClickListener(this);
            bt_reds_receive.setBackground(getResources().getDrawable(R.drawable.rounded_button_color));
        //已领取
        }else if (3 == status){
            bt_reds_receive.setText("已领取");
            bt_reds_receive.setOnClickListener(null);
            bt_reds_receive.setBackground(getResources().getDrawable(R.drawable.rounded_button_color_gray));
        //审核失败
        }else if (-1 == status){
            bt_reds_receive.setText("审核失败");
            bt_reds_receive.setOnClickListener(null);
            bt_reds_receive.setBackground(getResources().getDrawable(R.drawable.rounded_button_color_gray));
        }

        String text = mReds.getRedDetail();//领取规则
        String[] one = text.split(";");
        res_get_conditions1.setText(one[0]);
        res_get_conditions2.setText(one[1]);
        res_get_conditions3.setText(one[2]);
        res_get_conditions4.setText(one[3]);
        res_get_conditions5.setText(one[4]);
        res_get_conditions6.setText(one[5]);

    }

    protected void findViewById(){
        reds_name = (TextView) findViewById(R.id.reds_name);
        reds_price = (TextView) findViewById(R.id.reds_price);
        reds_num = (TextView) findViewById(R.id.reds_num);
        reds_spec = (TextView) findViewById(R.id.reds_spec);
        res_get_conditions1 = (TextView) findViewById(R.id.res_get_conditions1);
        res_get_conditions2 = (TextView) findViewById(R.id.res_get_conditions2);
        res_get_conditions3 = (TextView) findViewById(R.id.res_get_conditions3);
        res_get_conditions4 = (TextView) findViewById(R.id.res_get_conditions4);
        res_get_conditions5 = (TextView) findViewById(R.id.res_get_conditions5);
        res_get_conditions6 = (TextView) findViewById(R.id.res_get_conditions6);


        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        bt_reds_receive = (Button)findViewById(R.id.bt_reds_receive);
    }
    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImageAdapter(this, imageUrlList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_reds_receive:

                if (UsersUtil.isLogin(ReddenvelopebaseActivity.this)){

                    if (UsersUtil.isLee(ReddenvelopebaseActivity.this)){

                        if (0 == mReds.getRedStatus()){
                            Intent intent = new Intent(ReddenvelopebaseActivity.this,ApplyRedsActivity.class);
                            intent.putExtra(KeyConstants.IntentPageValues.Reds,mReds);
                            startActivity(intent);
                            finish();
                        }else if(2 == mReds.getRedStatus()){
                            RequestQueue mRequestQueue =  app.getRequestQueue();
                            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.RED_RECEIVE, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                    if (JsonParseUtil.isSuccess(mJsonObj)){
                                        if ("1".equals(mJsonObj.get("response_data").getAsString())){
                                            Toast.makeText(ReddenvelopebaseActivity.this,"领取成功",Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(ReddenvelopebaseActivity.this,"领取失败",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("redId",mReds.getRedId());
                                    params.put("userId",UsersUtil.getUserId(ReddenvelopebaseActivity.this));
                                    params.put("raStatus","3");
                                    return params;
                                }
                            };
                            mRequestQueue.add(mStringRequest);
                        }

                    }else{
                        //跳往李姓验证页面
                           Intent intent = new Intent(this,MemberActivity.class);
                           intent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,KeyConstants.IntentPageValues.forResult);
                           startActivityForResult(intent,RENZHENG);
                    }

                }else{
                    //跳登陆页面
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivityForResult(intent,LOGIN);
                }
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case LOGIN:

                    break;
                case RENZHENG:
                    Toast.makeText(ReddenvelopebaseActivity.this,"认证成功",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
