package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.PictureAdpter1;
import com.lijiadayuan.lishijituan.bean.Resources;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;

import java.util.ArrayList;

public class SharingResourceActivity extends BaseActivity implements OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
    private GridView gridView;
    ArrayList<Resources> mList;
    //图片ID数组
    private String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_resource);
        findViewById();
        initView();
        initData();
    }

    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        gridView = (GridView) findViewById(R.id.gridView);
    }

    protected void initView() {
        tvTitle.setText("共享资源");
        imageback.setOnClickListener(this);
    }
    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.SHARING_RESOURCE, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Resources.class);
                    if (mList.size() > 0){
                        images = new String[mList.size()];
                        for (int i = 0;i < mList.size(); i++){
                            images[i] = mList.get(i).getResImg();
                        }
                    }

                    PictureAdpter1 adapter = new PictureAdpter1(SharingResourceActivity.this,images,R.layout.more_item);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(SharingResourceActivity.this,CompanyProfileActivity.class);
                            Resources resource = mList.get(position);
                            mIntent.putExtra("resName", resource.getResName());
                            mIntent.putExtra("resUrl",resource.getResId());
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
