package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.LishiactivityAdpter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdpter1;
import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.WelfareGoodsBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;

public class LishiactivityActivity extends BaseActivity implements OnClickListener {
    private TextView tvTitle;
    private ImageView imageback;
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
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.GET_ALL_ACTIVITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Activites.class);
                    if (mList.size() > 0){
                        images = new String[mList.size()];
                        for (int i = 0;i < mList.size(); i++){
                            images[i] = mList.get(i).getActImg();
                        }
                    }

                    LishiactivityAdpter adapter = new LishiactivityAdpter(LishiactivityActivity.this,images,R.layout.redenvelope_image);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(LishiactivityActivity.this,RegistrationActivity.class);
                            mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,RegistrationActivity.GIFT_GOODS);
                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, ProductViewBean.getProductViewBeanList(mList.get(position), RegistrationActivity.GIFT_GOODS));
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

    protected void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        imageback.setOnClickListener(this);
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
