package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.OrderAdpter;
import com.lijiadayuan.lishijituan.bean.OrderBean;
import com.lijiadayuan.lishijituan.bean.ProductOrdersView;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyi on 16/4/21.
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener{
    private ListView mLvOrder;
    private TextView mTvTitle,mTvBack;
    private ImageView mIvBack;
    private SharedPreferences msp;
    private ArrayList<ProductOrdersView> mOrdersData;
    private QuickAdapter<ProductOrdersView> mAdpter;

    private static final int STATE_NOT_PAY = 0;
    private static final int STATE_WAIT_SEND = 1;
    private static final int STATE_WAIT_RECIEVE = 2;
    private static final int STATE_FINISHED = 3;
    private static final int STATE_ALL = 4;
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        msp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        mRequestQueue = app.getRequestQueue();
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //默认加载全部的商品订单信息
        Map<String,String> params = new HashMap<>();
        getDataByParams(params,UrlConstants.GET_ALL_ACTIVITY);

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

        //初始化适配器
        mOrdersData = new ArrayList<>();
        mAdpter = new QuickAdapter<ProductOrdersView>(MyOrderActivity.this,R.layout.item_order,mOrdersData) {
            @Override
            protected void convert(BaseAdapterHelper helper, ProductOrdersView item) {
                helper.setText(R.id.goods_name,item.getAddName());
                helper.setText(R.id.goods_price,item.getProPrice()+"");
                helper.setText(R.id.tv_unitprice,item.getPoAmount()+"");
                if (item.getPoStatus() == STATE_NOT_PAY){
                    //未支付
                    helper.setText(R.id.goods_status,"未支付");

                }else if (item.getPoStatus() == STATE_WAIT_SEND){
                    //待发货
                    helper.setText(R.id.goods_status,"待发货");
                }else if (item.getPoStatus() == STATE_WAIT_RECIEVE){
                    //待收获
                    helper.setText(R.id.goods_status,"待收获");
                }else if (item.getPoStatus() == STATE_FINISHED){
                    //已完成
                    helper.setText(R.id.goods_status,"已完成");
                }
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
                simpleDraweeView.setImageURI(Uri.parse(item.getProThumb()));
                helper.getView().findViewById(R.id.delete_goods).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 删除订单
                    }
                });
            }
        };
        mLvOrder.setAdapter(mAdpter);

    }

    @Override
    public void onClick(View view) {
        Map<String,String> params;
        String url = "";
        switch (view.getId()){
            case R.id.rb_all:
                params = new HashMap<>();
                params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
                getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO);
                break;
            case R.id.rb_obligations:
                params = new HashMap<>();
                params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
                params.put("status",STATE_NOT_PAY+"");
                getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO_BY_USERSTATE);
                break;
            case R.id.rb_wait_ship:
                params = new HashMap<>();
                params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
                params.put("status",STATE_WAIT_SEND+"");
                getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO_BY_USERSTATE);
                break;
            case R.id.rb_wait_receive:
                params = new HashMap<>();
                params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
                params.put("status",STATE_WAIT_RECIEVE+"");
                getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO_BY_USERSTATE);
                break;
            case R.id.rb_complete:
                params = new HashMap<>();
                params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
                params.put("status",STATE_FINISHED+"");
                getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO_BY_USERSTATE);
                break;

        }
    }


    private void getDataByParams(final Map<String,String> params,String url){
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObj)){
                    for (int i = 0 ; i < 10; i++){
                        ProductOrdersView mProductOrdersView = new ProductOrdersView();
                        mProductOrdersView.setAddArea("shanxi");
                        mProductOrdersView.setAddCity("晋城");
                        mProductOrdersView.setAddName("圣诞节");
                        mProductOrdersView.setPoStatus(STATE_FINISHED);
                        mProductOrdersView.setProPrice(12.33);
                        mProductOrdersView.setPoAmount(33.44);
                        mProductOrdersView.setProThumb("http://banbao.chazidian.com/uploadfile/2016-01-25/s145368924044608.jpg");
                        mOrdersData.add(mProductOrdersView);
                    }

                    mAdpter.clear();
                    mAdpter.addAll(mOrdersData);
                    //mAdpter.addAll(JsonParseUtil.toListByJson(mJsonObj.get("response_data").getAsJsonArray(), ProductOrdersView.class));
                    mAdpter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        mRequestQueue.add(mStringRequest);

    }
}
