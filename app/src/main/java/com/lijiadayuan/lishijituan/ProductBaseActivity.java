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


import com.lijiadayuan.lishijituan.adapter.ImageAdapter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.net.URL;
import java.util.ArrayList;

public class ProductBaseActivity extends BaseActivity implements OnClickListener {
    //购买页面
    public static final int BUY_GOODS = 1;
    //赠品页面
    public static final int GIFT_GOODS = 0;
    //视图bean
    private ProductViewBean mProductViewBean;

    //浏览商品次数  "http://192.168.0.103:8080/product/click?proId=LPRO000000001"
    private static final int LOGIN = 100;
    private static final int RENZHENG = 101;
    private static final int ORDEROK = 102;

    private ViewFlow mViewFlow;
    private WebView mWbGoodsInfo;
    private TextView mTvGoodsName,mTvGoodsNum,mTvGoodsPrice,mTvGoodsSpec;

    private CircleFlowIndicator mFlowIndicator;
    private Button mBtnReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_base);

        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);
        initView();
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
        if (mProductViewBean.getGoodsType() == BUY_GOODS) {
            mBtnReceive.setText("我要购买");
        }else if (mProductViewBean.getGoodsType() == GIFT_GOODS){
            mBtnReceive.setText("我要领取");
            //TODO 显示领取条件
        }
        mWbGoodsInfo.loadUrl(mProductViewBean.getGoodsInfoUrl());
        mTvGoodsName.setText(mProductViewBean.getGoodsName());
        mTvGoodsNum.setText(mProductViewBean.getGoodsNum());
        mTvGoodsPrice.setText(mProductViewBean.getGoodsPrice());
        mTvGoodsSpec.setText(mProductViewBean.getGoodsSpec());


    }

    protected void initView(){
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        mWbGoodsInfo = (WebView) findViewById(R.id.WebView);
        mTvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        mTvGoodsNum = (TextView) findViewById(R.id.tv_goods_num);
        mTvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
        mTvGoodsSpec = (TextView) findViewById(R.id.tv_goods_spec);
        mBtnReceive = (Button) findViewById(R.id.i_want_receive);
        mBtnReceive.setOnClickListener(this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case LOGIN:
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
               if (UsersUtil.isLogin(ProductBaseActivity.this)){
                   if(BUY_GOODS == mProductViewBean.getGoodsType()){

                       Intent intent = new Intent(this,OrderActivity.class);
                       intent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                       startActivity(intent);
                   }else if (GIFT_GOODS == mProductViewBean.getGoodsType()){
                       //判断是否认证为姓李
                       if (UsersUtil.isLee(ProductBaseActivity.this)){
                           //判断当前用户是否领取过福利商品的标识,0:未领取,未申请; 1:已申请未审核; 2:审核通过,可领取; 3:已领取; -1:审核失败
                           if(mProductViewBean.getGoodStatus() == 0){
                               //判断是否需要提交认证资料
                               if (mProductViewBean.getGoodsVerify() == 0){


                                   Intent intent = new Intent(this,OrderActivity.class);
                                   intent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                                   startActivityForResult(intent, ORDEROK);
                               }else{
                                   Intent intent = new Intent(this,SubmitDataActivity.class);
                                   intent.putExtra("shoppingId",mProductViewBean.getGoodsId());
                                   startActivityForResult(intent, ORDEROK);
                               }
                           }
                           //审核通过,可以领取
                           if (mProductViewBean.getGoodStatus() == 2){
                               Intent intent = new Intent(this,OrderActivity.class);
                               intent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                               startActivityForResult(intent, ORDEROK);
                           }

                           if (mProductViewBean.getGoodStatus() == 1){
                               Toast.makeText(ProductBaseActivity.this,"你已提交申请,请耐心等待",Toast.LENGTH_SHORT).show();
                           }

                           if (mProductViewBean.getGoodStatus() == 3){
                               Toast.makeText(ProductBaseActivity.this,"你已领取该商品",Toast.LENGTH_SHORT).show();
                           }

                           if (mProductViewBean.getGoodStatus() == -1){
                               Toast.makeText(ProductBaseActivity.this,"审核失败,请从新提交",Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(this,SubmitDataActivity.class);
                               intent.putExtra("shoppingId",mProductViewBean.getGoodsId());
                               startActivityForResult(intent, ORDEROK);
                           }


                       }else{
                           //跳往认证页面
                           Intent intent = new Intent(this,MemberActivity.class);
                           intent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,KeyConstants.IntentPageValues.forResult);
                           startActivityForResult(intent,RENZHENG);
                       }
                   }
               }else{
                   Intent intent = new Intent(this, LoginActivity.class);
                   startActivityForResult(intent, LOGIN);
               }
           break;
           case R.id.iv_back:
               finish();
               break;
       }
    }




}
