package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.lijiadayuan.lishijituan.adapter.LishiactivityAdpter;
import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LishiactivityActivity extends BaseActivity implements OnClickListener {
    private TextView tvTitle;
    private ListView mListView;

    //图片ID数组
    private String[] images;

    //活动列表数据
    private ArrayList<Activites> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState)         {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lishiactivity);

            //初始化view
            initView();
            //初始化数据
            initData();

        }

    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.GET_ALL_ACTIVITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    if (!mJsonObject.get("response_data").isJsonNull()){
                        mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                                .getAsJsonArray(),Activites.class);
                        QuickAdapter<Activites> mAdpter = new QuickAdapter<Activites>(LishiactivityActivity.this,R.layout.redenvelope_image,mList) {
                            @Override
                            protected void convert(BaseAdapterHelper helper, Activites item) {
                                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
                                simpleDraweeView.setImageURI(Uri.parse(item.getActImg()));
                            }
                        };
                        mListView.setAdapter(mAdpter);
                    }

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(LishiactivityActivity.this,LeeActivityDetails.class);
                            mIntent.putExtra(KeyConstants.IntentPageValues.Actvites,mList.get(position));
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
                params.put("userId", UsersUtil.getUserId(LishiactivityActivity.this));
                return params;
            }
        };
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);
    }

    protected void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        tvTitle = (TextView) findViewById(R.id.text_title);

        findViewById(R.id.iv_back).setOnClickListener(this);
        tvTitle.setText("李氏活动");
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
