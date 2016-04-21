package com.lijiadayuan.lishijituan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.adapter.OrderAdpter;
import com.lijiadayuan.lishijituan.bean.OrderBean;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/21.
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener{
    private ListView mLvOrder;
    private TextView mTvTitle,mTvBack;
    private ImageView mIvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ArrayList<OrderBean> mList = new ArrayList<>();
        for (int i = 0; i < 10;i++){
            OrderBean mOrderBean = new OrderBean();
            mOrderBean.setFreight("35");
            mOrderBean.setOrderStatus("已完成");
            mOrderBean.setShoppingName("六龙和磁化活水器");
            mOrderBean.setShoppingNum("2");
            mOrderBean.setShoppingPrice("250");
            mOrderBean.setShoppingUnitPrice("220");
            mList.add(mOrderBean);
        }
        OrderAdpter mOrderAdpter = new OrderAdpter(mList,this);
        mLvOrder.setAdapter(mOrderAdpter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mLvOrder = (ListView) findViewById(R.id.listview_order);
        findViewById(R.id.rb_all).setOnClickListener(this);
        findViewById(R.id.rb_obligations).setOnClickListener(this);
        findViewById(R.id.rb_wait_ship).setOnClickListener(this);
        findViewById(R.id.rb_wait_receive).setOnClickListener(this);
        findViewById(R.id.rb_complete).setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.text_title);
        mTvBack = (TextView) findViewById(R.id.text_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_all:

                break;
            case R.id.rb_obligations:

                break;
            case R.id.rb_wait_ship:

                break;
            case R.id.rb_wait_receive:

                break;
            case R.id.rb_complete:

                break;

        }
    }
}
