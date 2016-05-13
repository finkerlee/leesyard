package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdpter1;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.WelfareGoodsBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MoreActivity extends BaseActivity {
    private GridView gridView;
    private TextView tvTitle,tvTitleback;

    //图片ID数组
    private String[] images;

    private  ArrayList<WelfareGoodsBean> mList;

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
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.WELFARE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                     mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),WelfareGoodsBean.class);
                    QuickAdapter<WelfareGoodsBean> mAdpter = new QuickAdapter<WelfareGoodsBean>(MoreActivity.this,R.layout.more_item,mList) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, WelfareGoodsBean item) {
                            SimpleDraweeView mPic = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage1);
                            mPic.setImageURI(Uri.parse(item.getBenThumb()));
                        }
                    };
                    gridView.setAdapter(mAdpter);
//                    if (mList.size() > 0){
//                        images = new String[mList.size()];
//                        for (int i = 0;i < mList.size(); i++){
//                            images[i] = mList.get(i).getBenThumb();
//                        }
//                    }
//
//                    PictureAdpter1 adapter = new PictureAdpter1(MoreActivity.this,images,R.layout.more_item);
//                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(MoreActivity.this,ProductBaseActivity.class);
                            mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,ProductBaseActivity.GIFT_GOODS);
                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, ProductViewBean.getProductViewBean(mList.get(position),ProductBaseActivity.GIFT_GOODS));
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

    /**
     * 初始化控件
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        ImageView mIv = (ImageView) findViewById(R.id.iv_address);
        mIv.setImageResource(R.drawable.btn_return);
        findViewById(R.id.iv_messsage).setVisibility(View.GONE);
        tvTitleback= (TextView) findViewById(R.id.title_back);
        tvTitleback.setText("返回");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("更多");
    }

}