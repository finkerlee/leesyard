package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;
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
        params.put("userId",msp.getString(KeyConstants.UserInfoKey.userId,""));
        getDataByParams(params,UrlConstants.QUERY_ALL_GOODS_INFO);

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

        findViewById(R.id.iv_back).setOnClickListener(this);
        mTvTitle.setText("我的订单");
        //初始化适配器
        mOrdersData = new ArrayList<>();
        mAdpter = new QuickAdapter<ProductOrdersView>(MyOrderActivity.this,R.layout.item_order,mOrdersData) {
            @Override
            protected void convert(BaseAdapterHelper helper, final ProductOrdersView item) {
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
                if (item.getProThumb() == null){
                    simpleDraweeView.setImageURI(Uri.parse("res://com.lijiadayuan.lishijituan/" + R.drawable.user_normol_head_image));
                }else{
                    simpleDraweeView.setImageURI(Uri.parse(item.getProThumb()));
                }
                helper.getView().findViewById(R.id.delete_goods).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 删除订单
                        new AlertDialog.Builder(MyOrderActivity.this)
                                .setTitle("删除订单").setMessage("确定删除订单").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringRequest mStringRequest = new StringRequest(Request.Method.POST,UrlConstants.DELETE_ORDER, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                        if (JsonParseUtil.isSuccess(mJsonObj)){
                                            String status = mJsonObj.get("response_data").getAsString();
                                            if ("1".equals(status)){
                                                Toast.makeText(MyOrderActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                                                mOrdersData.remove(item);
                                                if (mOrdersData.size() == 0){
                                                    findViewById(R.id.layout_no_order).setVisibility(View.VISIBLE);
                                                    mLvOrder.setVisibility(View.GONE);
                                                    return;
                                                }
                                                mAdpter.clear();
                                                mAdpter.addAll(mOrdersData);
                                                mAdpter.notifyDataSetChanged();
                                            }else{
                                                Toast.makeText(MyOrderActivity.this,"删除失败",Toast.LENGTH_LONG).show();
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
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("poId",item.getPoId());
                                        return params;
                                    }
                                };
                                mRequestQueue.add(mStringRequest);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();



                    }
                });
            }
        };
        mLvOrder.setAdapter(mAdpter);

    }

    @Override
    public void onClick(View view) {
        Map<String,String> params;
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

            case R.id.iv_back:
                finish();
                break;

        }
    }


    private void getDataByParams(final Map<String,String> params,String url){
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObj)){
                    JsonArray mJsonArray = mJsonObj.get("response_data").getAsJsonArray();
                    if (mJsonArray.size() ==0){
                        findViewById(R.id.layout_no_order).setVisibility(View.VISIBLE);
                        mLvOrder.setVisibility(View.GONE);
                        mAdpter.clear();
                        mAdpter.notifyDataSetChanged();
                    }else{
                        findViewById(R.id.layout_no_order).setVisibility(View.GONE);
                        mLvOrder.setVisibility(View.VISIBLE);
                        mOrdersData =  JsonParseUtil.toListByJson(mJsonArray,ProductOrdersView.class);
                        mAdpter.clear();
                        mAdpter.addAll(mOrdersData);
                        //mAdpter.addAll(JsonParseUtil.toListByJson(mJsonObj.get("response_data").getAsJsonArray(), ProductOrdersView.class));
                        mAdpter.notifyDataSetChanged();
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
                return params;
            }
        };
        mRequestQueue.add(mStringRequest);

    }
}
