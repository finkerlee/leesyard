package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
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
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Tickets;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketTicapplyActivity extends BaseActivity implements View.OnClickListener {


    private static final int LOGIN = 100;
    private static final int RENZHENG = 101;

    private Boolean isLogin;

    private Button bt_reds_receive;//申请领取

    private SharedPreferences mSharedPreferences;

    private TextView reds_name;//名字
    private TextView  reds_price;//红包金额
    private TextView reds_num;//红包剩余个数
    private TextView reds_spec;//介绍
    private TextView res_get_conditions1,res_get_conditions2,res_get_conditions3,res_get_conditions4,res_get_conditions5,res_get_conditions6;//l领取说明

    private TextView reds_AsOfTime;//截止时间

    private Tickets mTickets;

    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_ticapply);

        mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);

        isLogin = mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
        //卡票信息
        mTickets = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.Tickets);
        String img = mTickets.getTktImg();
        String[] mImgList = img.split(",");
        for (String s :mImgList ){
            imageUrlList.add(s);
        }
        initView();
        initBanner(imageUrlList);

        //将数据加载到视图
        setViewByData();

    }

    private void setViewByData() {
        reds_name.setText(mTickets.getTktName());//名称
        reds_price.setText("¥"+mTickets.getTktAmount());//金额
        reds_num.setText(mTickets.getTktStock()+"个");//数量

        CharSequence charSequence = Html.fromHtml(mTickets.getTktIntro());
        reds_spec.setText(charSequence);
        reds_spec.setMovementMethod(LinkMovementMethod.getInstance());


        String text = mTickets.getTktDetail();//领取规则

        String[] one = text.split(";");
        res_get_conditions1.setText(one[0]);
        res_get_conditions2.setText(one[1]);
//        res_get_conditions3.setText(one[2]);
//        res_get_conditions4.setText(one[3]);
//        res_get_conditions5.setText(one[4]);
//        res_get_conditions6.setText(one[5]);

    }

    protected void initView(){
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
        bt_reds_receive.setOnClickListener(this);

        int status = mTickets.getTktStatus();
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
            //0:未领取,未申请; 1:已申请未审核; 2:审核通过,可领取; 3:已领取; -1:审核失败
            case R.id.bt_reds_receive:
                if (UsersUtil.isLogin(TicketTicapplyActivity.this)){

                    if (UsersUtil.isLee(TicketTicapplyActivity.this)){

                        if (0 == mTickets.getTktStatus()){
                            Intent intent = new Intent(TicketTicapplyActivity.this,ApplyTicketActivity.class);
                            intent.putExtra(KeyConstants.IntentPageValues.Tickets,mTickets);
                            startActivity(intent);
                            finish();
                        }else if(2 == mTickets.getTktStatus()){
                            RequestQueue mRequestQueue =  app.getRequestQueue();
                            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.RECEIVE_TICAPPLY, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                    if (JsonParseUtil.isSuccess(mJsonObj)){
                                        if ("1".equals(mJsonObj.get("response_data").getAsString())){
                                            Toast.makeText(TicketTicapplyActivity.this,"领取成功",Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(TicketTicapplyActivity.this,"领取失败",Toast.LENGTH_LONG).show();
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
                                    params.put("tktId",mTickets.getTktId());
                                    params.put("userId",UsersUtil.getUserId(TicketTicapplyActivity.this));
                                    params.put("taStatus","3");
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

}
