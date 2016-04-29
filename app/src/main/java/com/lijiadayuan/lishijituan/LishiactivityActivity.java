package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;

import java.util.ArrayList;

public class LishiactivityActivity extends BaseActivity implements OnClickListener {
    private ListView mLvActivity;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //图片ID数组
    private int[] images = new int[]{
            R.drawable.activity1, R.drawable.activity2,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishiactivity);

        //初始化view
        initView();
        //初始化数据
        initData();

//        adapter = new PictureAdapter(images, this, R.layout.image_ativity);
//        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.image_ativity);
//        initView();
    }

    private void initData() {
        //创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, UrlConstants.GET_ALL_ACTIVITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (JsonParseUtil.isSuccess(JsonParseUtil.getJsonByString(response).getAsJsonObject())){
                    //TODO 获得数据 展示到listview
//                    ArrayList<Integer> mList = new ArrayList<>();
//                    for (int i = 0;i<images.length;i++){
//                        mList.add(images[i]);
//                    }
//
//                    QuickAdapter<Integer> mAdpter = new QuickAdapter<Integer>(LishiactivityActivity.this,R.layout.image_ativity,mList) {
//                        @Override
//                        protected void convert(BaseAdapterHelper helper, Integer item) {
//                            helper.setBackgroundRes(R.id.itemImage,item);
//                        }
//                    };
//                    mLvActivity.setAdapter(mAdpter);


                }else{
                    Toast.makeText(LishiactivityActivity.this,"获取数据失败",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(mStringRequest);
    }

    protected void initView() {

        mLvActivity = (ListView) findViewById(R.id.mListView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        tvTitle.setText("李氏活动");

//        ArrayList<Integer> mList = new ArrayList<>();
//        for (int i = 0;i<images.length;i++){
//            mList.add(images[i]);
//        }
//
//        QuickAdapter<Integer> mAdpter = new QuickAdapter<Integer>(LishiactivityActivity.this,R.layout.image_ativity,mList) {
//            @Override
//            protected void convert(BaseAdapterHelper helper, Integer item) {
//                helper.setBackgroundRes(R.id.itemImage,item);
//            }
//        };
//        mLvActivity.setAdapter(mAdpter);
//        mLvActivity.setOnClickListener(this);
//        mLvActivity.setAdapter(adapter);
//        mLvActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> images0, View images1, int images2, long images3) {
////                images0.setOnClickListener(new OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        startActivity(new Intent(CultureActivity.this,RegistrationActivity.class));
////                    }
////                });
//                if (1 == images2)
//                    startActivity(new Intent(LishiactivityActivity.this, EventdetailsActivity.class));
//                else if (0 == images2)
//                    startActivity(new Intent(LishiactivityActivity.this, EventdetailsActivity.class));
//            }
//        });
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
