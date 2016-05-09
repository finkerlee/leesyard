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
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyi on 16/5/8.
 */
public class SearchResultActivity extends BaseActivity  implements View.OnClickListener{
    private ListView mLv;
    private ArrayList<Product> mList;
    private ImageView iv_back;
    private String key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        key =  getIntent().getStringExtra("keyword");
        initView();
        initData();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(SearchResultActivity.this,ProductBaseActivity.class);
                ProductViewBean mProductViewBean = ProductViewBean.getProductViewBean(mList.get(i), ProductBaseActivity.BUY_GOODS);
                Log.i("main",mProductViewBean.getGoodsType()+"");
                mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                startActivity(mIntent);
            }
        });
    }

    private void initData() {
        RequestQueue mRequestQueue = app.getRequestQueue();
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.SEARCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObj)){
                    if (!mJsonObj.get("response_data").isJsonNull()){
                       JsonArray mJsonArray =  mJsonObj.get("response_data").getAsJsonArray();
                        if (mJsonArray.size()>0){
                            mList = JsonParseUtil.toListByJson(mJsonArray, Product.class) ;
                            QuickAdapter<Product> mAdpter = new QuickAdapter<Product>(SearchResultActivity.this,R.layout.item_search_result,mList) {
                                @Override
                                protected void convert(BaseAdapterHelper helper, Product item) {
                                    helper.setText(R.id.tv_name,item.getProName());
                                    helper.setText(R.id.tv_price,item.getProPrice()+"");
                                    SimpleDraweeView msv = (SimpleDraweeView) helper.getView().findViewById(R.id.iv_pic);
                                    msv.setImageURI(Uri.parse(item.getProThumb()));
                                }
                            };
                            mLv.setAdapter(mAdpter);
                        }

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
                Map<String, String> params = new HashMap<>();
                params.put("key",key);
                return params;
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv_search_result);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
