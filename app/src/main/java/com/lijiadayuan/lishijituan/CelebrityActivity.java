package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.Celebrities;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;

import java.util.ArrayList;

public class CelebrityActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvTitle;
    private ImageView imageback;
    ArrayList<Celebrities> mList;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity);
        findViewById();
        initView();
        initdata();
    }
    private void findViewById(){
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        listView= (ListView) findViewById(R.id.celebrity_listview);
    }
    private void initView(){
        tvTitle.setText("李氏名人");
        imageback.setOnClickListener(this);
    }
    private void initdata(){
        //请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.ALL_CELEBRITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Celebrities.class);
                    QuickAdapter<Celebrities> adapter=new QuickAdapter<Celebrities>(CelebrityActivity.this,R.layout.celebrity,mList) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, Celebrities item) {
                            SimpleDraweeView mPic= (SimpleDraweeView) helper.getView().findViewById(R.id.im_celebrity);
                            mPic.setImageURI(Uri.parse(item.getCeleImg()));
                            helper.setText(R.id.celebrity_title, item.getCeleTitle());
                            helper.setText(R.id.celebrith_name,item.getCeleName());
//                            helper.getView().findViewById(R.id.im_celebrity).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
                        }
                    };
//                    PictureAdpter1 adapter = new PictureAdpter1(CelebrityActivity.this,R.layout.celebrity);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent mIntent = new Intent(CelebrityActivity.this,CelebrityDetailsActivity.class);
                            Celebrities celebrities = mList.get(position);
                            mIntent.putExtra("celeName", celebrities.getCeleName());
                            mIntent.putExtra("celeId",celebrities.getCeleId());
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
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
