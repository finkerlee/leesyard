package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.adapter.ImageAdapter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;

public class TicketTicapplyActivity extends Activity implements View.OnClickListener {

    //购买页面
    public static final int BUY_GOODS = 1;
    //赠品页面
    public static final int GIFT_GOODS = 0;
    //视图bean
    private ProductViewBean mProductViewBean;

    private Boolean isLogin;

    private Button bt_reds_receive;//申请领取

    private SharedPreferences mSharedPreferences;

    private TextView reds_name;//名字
    private TextView  reds_price;//红包金额
    private TextView reds_num;//红包剩余个数
    private TextView reds_spec;//介绍
    private TextView res_get_conditions1,res_get_conditions2,res_get_conditions3,res_get_conditions4,res_get_conditions5,res_get_conditions6;//l领取说明

    private TextView reds_AsOfTime;//截止时间

    private ViewFlow mViewFlow;
    ArrayList<String> linkUrlArray= new ArrayList<String>();
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_ticapply);

        mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);

        isLogin = mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin,false);
        //红包信息
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);

        imageUrlList.add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
        imageUrlList.add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");

        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");
        findViewById();
        initBanner(imageUrlList);

        //将数据加载到视图
        setViewByData();

    }

    private void setViewByData() {
        reds_name.setText(mProductViewBean.getGoodsName());//名称
        reds_price.setText("¥"+mProductViewBean.getGoodsPrice());//金额
        reds_num.setText(mProductViewBean.getGoodsNum()+"个");//数量
        reds_spec.setText(mProductViewBean.getGoodsIntro());//


        String text = mProductViewBean.getGoodsSpec();//领取规则

        Log.e("Log","text ==== "+ mProductViewBean.getGoodsSpec());
        String[] one = text.split(";");
        res_get_conditions1.setText(one[0]);
        res_get_conditions2.setText(one[1]);
//        res_get_conditions3.setText(one[2]);
//        res_get_conditions4.setText(one[3]);
//        res_get_conditions5.setText(one[4]);
//        res_get_conditions6.setText(one[5]);

    }

    protected void findViewById(){
        reds_name = (TextView) findViewById(R.id.reds_name);
        reds_price = (TextView) findViewById(R.id.reds_price);
        reds_num = (TextView) findViewById(R.id.reds_num);
        reds_AsOfTime = (TextView) findViewById(R.id.reds_AsOfTime);
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
        Toast.makeText(TicketTicapplyActivity.this, "申请领取", Toast.LENGTH_SHORT).show();

    }

}
