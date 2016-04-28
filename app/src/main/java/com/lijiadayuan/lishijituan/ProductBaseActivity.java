package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.adapter.ImageAdapter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.WelfareGoodsBean;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;

public class ProductBaseActivity extends BaseActivity implements OnClickListener {
    //购买页面
    public static final int BUY_GOODS = 1;
    //赠品页面
    public static final int GIFT_GOODS = 0;
    //视图bean
    private ProductViewBean mProductViewBean;

    private ViewFlow mViewFlow;
    private ImageView iv_back;
    private WebView mWbGoodsInfo;
    private TextView mTvGoodsName,mTvGoodsNum,mTvGoodsPrice,mTvGoodsSpec;





    ArrayList<String> linkUrlArray= new ArrayList<String>();
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    private Button mBtnReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_base);
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);

        imageUrlList
                .add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
        imageUrlList
                .add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");
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
        initBanner(imageUrlList);
        //将数据加载到视图
        setViewByData();
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
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.i_want_receive:
               Intent intent = new Intent(this,ReceiveActivity.class);
               startActivity(intent);
           break;
           case R.id.iv_back:
               finish();
               break;
       }
    }
}
