package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.Resources;
import com.lijiadayuan.lishijituan.bean.Topics;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.DateTimeUtil;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;

import org.apache.log4j.lf5.util.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadlineActivity extends BaseActivity {
    private TextView tvTitle;
    private ImageView imageback;
    private ListView listView;
    private ArrayList<Topics> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headline);
        //初始化view
        initView();
        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        RequestQueue mRequestQueue = app.getRequestQueue();
        StringRequest mRequest = new StringRequest(Request.Method.GET, UrlConstants.HEAD_LINE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 if (JsonParseUtil.isSuccess(JsonParseUtil.getJsonByString(response).getAsJsonObject())){
                     JsonArray mJsonArray = JsonParseUtil.getJsonByString(response).getAsJsonObject().get("response_data").getAsJsonArray();
                     mList = JsonParseUtil.toListByJson(mJsonArray,Topics.class);
                     QuickAdapter<Topics> mAdpter = new QuickAdapter<Topics>(HeadlineActivity.this,R.layout.aheadlinedata,mList) {
                         @Override
                         protected void convert(BaseAdapterHelper helper, Topics item) {
               //              helper.setText(R.id.tv_title,item.getTopTitle());
                             helper.setText(R.id.tv_title,item.getTopTitle()+"");
                             helper.setText(R.id.tv_headline_date,DateTimeUtil.getTargetDate( item.getTopDate()*1000));
                             SimpleDraweeView mIvPic = (SimpleDraweeView) helper.getView().findViewById(R.id.head_image_pic);
                             mIvPic.setImageURI(Uri.parse(item.getTopImg()));
                         }
                     };
                     listView.setAdapter(mAdpter);
                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                             Intent hintent=new Intent(HeadlineActivity.this,HeadlineDetailsActivity.class);
                             Topics mtopics=mList.get(position);
                             hintent.putExtra("topTitle",mtopics.getTopTitle());
                             hintent.putExtra("topId",mtopics.getTopId());
                             startActivity(hintent);
                         }
                     });
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("main",error.getMessage());
            }
        });
        mRequestQueue.add(mRequest);
    }

    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        listView= (ListView) findViewById(R.id.lv_headline);
        tvTitle.setText("大院头条");
        //dataList= new ArrayList<Map<String, Object>>();
        //simp_adapter=new SimpleAdapter(this,getData(), R.layout.aheadlinedata,new String[]{"iv_date","iv_text"},new int[]{R.id.lv_headline_date,R.id.imageView2} );
        //listView.setAdapter(simp_adapter);
    }
//    public List<Map<String, Object>> getData() {
//        for(int i=0;i<10;i++){
//            Map<String,Object>map=new HashMap<String,Object>();
//            map.put("iv_date","2016-03-17"+" "+"16:2"+i);
//            map.put("iv_text","李氏动态");
//            dataList.add(map);
//        }
//        return dataList;
//    }
}
