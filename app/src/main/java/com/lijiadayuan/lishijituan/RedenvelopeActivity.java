package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.FindAdpter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.adapter.RedAdpter;
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Reds;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedenvelopeActivity extends BaseActivity implements OnClickListener {

    private GridView gridView;
    private TextView tvTitle;
    private String[] images;
    private ArrayList<Reds> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redenvelope);
        gridView = (GridView) findViewById(R.id.culture_gridView);

        findViewById(R.id.iv_back).setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.text_title);
        tvTitle.setText("红包");
        initData();

    }

    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.RED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Reds.class);
                    if (mList.size() > 0){
                        images = new String[mList.size()];
                        for (int i = 0;i < mList.size(); i++){
                            images[i] = mList.get(i).getRedImg();
                        }
                    }

                    RedAdpter adapter = new RedAdpter (RedenvelopeActivity.this,images,R.layout.redenvelope_image);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(RedenvelopeActivity.this,ReddenvelopebaseActivity.class);
                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mList.get(position));
                            startActivity(mIntent);
                            finish();
                        }
                    });
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", UsersUtil.getUserId(RedenvelopeActivity.this));
                return params;
            }
        };
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
