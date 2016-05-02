<<<<<<< HEAD
package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.FindAdpter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdpter1;
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.WelfareGoodsBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;




public class FindActivity extends BaseActivity {
    private GridView gridView;//image
    private TextView tvTitle;
    private String[] images;
    private ArrayList<Product> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        gridView = (GridView) findViewById(R.id.gridView);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("发现");
        initData();
    }

    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Product.class);
                    if (mList.size() > 0){
                        images = new String[mList.size()];
                        for (int i = 0;i < mList.size(); i++){
                            images[i] = mList.get(i).getProImg();
                        }
                    }

                    FindAdpter adapter = new FindAdpter (FindActivity.this,images,R.layout.image);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(FindActivity.this,ProductBaseActivity.class);
                          mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,ProductBaseActivity.BUY_GOODS);
                          mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, ProductViewBean.getProductViewBeanList(mList.get(position), ProductBaseActivity.BUY_GOODS));
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

=======
package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.FindAdpter;
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;

public class FindActivity extends BaseActivity {
    private GridView gridView;
    private TextView tvTitle;
    //图片ID数组
    private String[] images;

    private ArrayList<Product> mlist;

    private final String url = "http://192.168.0.101:8080/product/all";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        initView();
        initData();

    }

          //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue requestQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest r = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JsonObject JsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(JsonObject)){
                    mlist =  JsonParseUtil.toListByJson(JsonObject.get("response_data")
                            .getAsJsonArray(),Product.class);

                    if (mlist.size() > 0){
                        images = new String[mlist.size()];
                        for (int i = 0;i < mlist.size(); i++){
                            images[i] = mlist.get(i).getProImg();
                        }
                    }

                    FindAdpter adapter = new FindAdpter(FindActivity.this,images,R.layout.image);
                    System.out.println("11111111");
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(FindActivity.this,ProductBaseActivity.class);
//                            mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,ProductBaseActivity.GIFT_GOODS);
//                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, ProductViewBean.getProductViewBeanList(mlistist.get(position),ProductBaseActivity.GIFT_GOODS));
                            startActivity(mIntent);
                        }
                    });
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Log","error  == " + error);
            }
        });
        // 将请求添加到请求队列中(即发送请求)
        requestQueue.add(r);

    }




    /**
     * 设置视图
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("发现");
    }






>>>>>>> 42c4e477d66953011679224139888888180ceb1a
}