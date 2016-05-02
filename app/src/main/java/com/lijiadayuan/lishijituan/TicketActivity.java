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
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Resources;
import com.lijiadayuan.lishijituan.bean.Tickets;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketActivity extends BaseActivity implements OnClickListener {
    private GridView gridView;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    private ArrayList<Tickets> mTicketList;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.kp1, R.drawable.kp2, R.drawable.kp3,
            R.drawable.kp4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        adapter = new PictureAdapter(images, this, R.layout.large_image);
        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.large_image);
        findViewById();
        initView();
        initData();


    }
    //初始化数据
    private void initData() {
        mTicketList= new ArrayList<>();
        for (int i =0;i < 5;i++){
            Tickets mTickets = new Tickets();
            mTickets.setTktImg("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
            mTicketList.add(mTickets);

        }

        QuickAdapter<Tickets> mAdpter = new QuickAdapter<Tickets>(TicketActivity.this,R.layout.large_image,mTicketList) {
            @Override
            protected void convert(BaseAdapterHelper helper, Tickets item) {
               // helper.setBackgroundRes(R.id.itemImage, item.getTktImg());

                SimpleDraweeView miv = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
                miv.setImageURI(Uri.parse(item.getTktImg()));
            }
        };
        gridView.setAdapter(mAdpter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent mIntent = new Intent(TicketActivity.this, TicketTicapplyActivity.class);
                Tickets tickets = mTicketList.get(position);
                mIntent.putExtra("tktName",tickets.getTktName());
                startActivity(mIntent);
            }
        });


        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.TICKET, new Response.Listener<String>() {
            /** 重写onResponse,以实现请求响应的操作 **/
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("main", error.toString());

            }
        });
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);

    }

    protected void findViewById() {
        gridView = (GridView) findViewById(R.id.culture_gridView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
    }
    protected void initView() {
        tvTitle.setText("卡票");
        imageback.setOnClickListener(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(TicketActivity.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
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
