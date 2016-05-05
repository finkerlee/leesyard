package com.lijiadayuan.lishijituan;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.adapter.RedAdpter;
import com.lijiadayuan.lishijituan.adapter.TicketAdpter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Reds;
import com.lijiadayuan.lishijituan.bean.Resources;
import com.lijiadayuan.lishijituan.bean.Tickets;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketActivity extends BaseActivity implements OnClickListener {
    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private String[] images;
    private ArrayList<Tickets> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        findViewById();
        initView();

        initData();
    }



    protected void findViewById() {
        gridView = (GridView) findViewById(R.id.culture_gridView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }

    protected void initView() {
//        tvTitle.setText("卡票");
        imageback.setOnClickListener(this);

    }


    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.TICKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Tickets.class);
                    if (mList.size() > 0){
                        images = new String[mList.size()];
                        for (int i = 0;i < mList.size(); i++){
                            images[i] = mList.get(i).getTktImg();
                        }
                    }

                    TicketAdpter adapter = new TicketAdpter (TicketActivity.this,images,R.layout.redenvelope_image);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(TicketActivity.this,TicketTicapplyActivity.class);
                            mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,TicketTicapplyActivity.GIFT_GOODS);
                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, ProductViewBean.getTicketViewBeanList(mList.get(position), ProductBaseActivity.GIFT_GOODS));
                            startActivity(mIntent);
                        }
                    });
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
