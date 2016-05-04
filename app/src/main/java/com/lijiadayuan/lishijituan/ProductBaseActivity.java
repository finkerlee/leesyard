package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lijiadayuan.lishijituan.adapter.ImageAdapter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.bean.WelfareGoodsBean;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.net.URL;
import java.util.ArrayList;

public class ProductBaseActivity extends BaseActivity implements OnClickListener {
    //购买页面
    public static final int BUY_GOODS = 1;
    //赠品页面
    public static final int GIFT_GOODS = 0;
    //视图bean
    private ProductViewBean mProductViewBean;
    //
    private Boolean isLogin;

    //浏览商品次数  "http://192.168.0.103:8080/product/click?proId=LPRO000000001"
    private String proId ;


    private static final int LOGIN = 100;
    private static final int RENZHENG = 101;
    private static final int ORDEROK = 102;

    private ViewFlow mViewFlow;
    private ImageView iv_back;
    private WebView mWbGoodsInfo;
    private TextView mTvGoodsName,mTvGoodsNum,mTvGoodsPrice,mTvGoodsSpec;

    private SharedPreferences mSharedPreferences;

    ArrayList<String> linkUrlArray= new ArrayList<String>();
    private CircleFlowIndicator mFlowIndicator;
    private Button mBtnReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_base);
        mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        isLogin = mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);
        linkUrlArray
                .add("");
        linkUrlArray
                .add("");
        linkUrlArray
                .add("");
        linkUrlArray
                .add("");
        initView();
        //加载轮播图
        if(null == mProductViewBean.getPicList()){
            ArrayList<String> mlist =new ArrayList<>();
            mlist.add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
            mProductViewBean.setPicList(mlist);
        }
        initBanner(mProductViewBean.getPicList());
        //将数据加载到视图
        setViewByData();

        initIncrease();
    }

    private void initIncrease() {



    }

    /**
     * 将数据加载到视图
     */

    private void setViewByData() {

        mWbGoodsInfo.loadUrl(mProductViewBean.getGoodsInfoUrl());
        mTvGoodsName.setText(mProductViewBean.getGoodsName());
        mTvGoodsNum.setText(mProductViewBean.getGoodsNum());
        mTvGoodsPrice.setText(mProductViewBean.getGoodsPrice());
        mTvGoodsSpec.setText(mProductViewBean.getGoodsSpec());

        proId = (String) mProductViewBean.getGoodsInfoUrl().subSequence(47,60);
        String increaseUrl = "http://192.168.0.103:8080/product/click?proId=" + proId;

        Log.e("Log", "proId === " + proId);

        // 创建请求队列
        RequestQueue rq = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest sr = new StringRequest(Request.Method.POST, increaseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Log","response ============ " + response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // 将请求添加到请求队列中(即发送请求)
        rq.add(sr);
    }

    protected void initView(){
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        mWbGoodsInfo = (WebView) findViewById(R.id.WebView);
        mTvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        mTvGoodsNum = (TextView) findViewById(R.id.tv_goods_num);
        mTvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        mTvGoodsSpec = (TextView) findViewById(R.id.tv_goods_spec);
        mBtnReceive = (Button) findViewById(R.id.i_want_receive);

        mBtnReceive.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        if (mProductViewBean.getGoodsType() == BUY_GOODS) {
            mBtnReceive.setText("我要购买");
        }else{
            mBtnReceive.setText("我要领取");
        }
    }

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImageAdapter(this, imageUrlList,
                linkUrlArray).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case LOGIN:
                    isLogin = data.getBooleanExtra(KeyConstants.UserInfoKey.userIsLogin,false);
                    break;
                case RENZHENG:
                    Toast.makeText(this,"认证成功,请耐心等待",Toast.LENGTH_LONG).show();
                    break;
                case ORDEROK:
                    Toast.makeText(this,"下单成功",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.i_want_receive:
               //如果已经登陆
               if (mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin,false)){
                   if("我要购买".equals(mBtnReceive.getText())){
                       Intent intent = new Intent(this,OrderActivity.class);
                       intent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                       startActivity(intent);
                   }else{
                       if (mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIfLee,false)){
                           Intent intent = new Intent(this,OrderActivity.class);
                           intent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                           startActivityForResult(intent, ORDEROK);
                       }else{
                           Intent intent = new Intent(this,MemberActivity.class);
                           intent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,KeyConstants.IntentPageValues.forResult);
                           startActivityForResult(intent,RENZHENG);
                       }
                   }
               }else{
                   Intent intent = new Intent(this,LoginActivity.class);
                   startActivityForResult(intent,LOGIN);
               }
           break;
           case R.id.iv_back:
               finish();
               break;
       }
    }




}
