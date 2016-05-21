package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.FindAdpter;
import com.lijiadayuan.lishijituan.adapter.FindSpacesItemDecoration;
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.view.AddressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;//image
    private TextView tvTitle;
    private List<Product> mList;
    private AddressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.iv_address).setOnClickListener(this);
        findViewById(R.id.iv_messsage).setOnClickListener(this);
        tvTitle.setText("发现");

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

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

                    FindAdpter mAdpter = new FindAdpter(FindActivity.this,mList);
                    mRecyclerView.setAdapter(mAdpter);

                    //设置间距
                    FindSpacesItemDecoration decoration=new FindSpacesItemDecoration(16);
                    mRecyclerView.addItemDecoration(decoration);
//                    QuickAdapter<Product> mAdpter = new QuickAdapter<Product>(FindActivity.this,R.layout.image,mList) {
//                        @Override
//                        protected void convert(BaseAdapterHelper helper, Product item) {
//                           SimpleDraweeView msp = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
//                            msp.setImageURI(Uri.parse(item.getProThumb()));
////                            AutoUtils.autoSize(helper.getView());
//                        }
//                    };
//
//                    gridView.setAdapter(mAdpter);
//                    gridView.setOnItemClickListener(new OnItemClickListener() {
//                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                            Intent mIntent = new Intent(FindActivity.this,ProductBaseActivity.class);
//                            ProductViewBean mProductViewBean = ProductViewBean.getProductViewBean(mList.get(position), ProductBaseActivity.BUY_GOODS);
//                            Log.i("main",mProductViewBean.getGoodsType()+"");
//                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
//                            startActivity(mIntent);
//                        }
//                    });
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
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_address:
               dialog = new AddressDialog(FindActivity.this, R.style.protocol_dialog,HomeActivity.mCurrentAddress);
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog.show();
               break;
           case R.id.iv_messsage:
               Intent mIntent = new Intent(FindActivity.this,MymessageActivity.class);
               startActivity(mIntent);
               break;
       }
    }

}

