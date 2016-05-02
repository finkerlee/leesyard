package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
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
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdapter;
import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.bean.Activites;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.ArrayList;

public class LishiactivityActivity extends BaseActivity implements OnClickListener {
    private ListView mLvActivity;
    private TextView tvTitle;
    private ImageView imageback;
    private PictureAdapter adapter ;
    //活动列表数据
    private ArrayList<Activites> mActivitesList;
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
        setListener();

//        adapter = new PictureAdapter(images, this, R.layout.image_ativity);
//        PictureAdapter adapter = new PictureAdapter(images, this, R.layout.image_ativity);
//        initView();
    }

    private void setListener() {
                mLvActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> images0, View images1, int images2, long images3) {
//                images0.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(CultureActivity.this,RegistrationActivity.class));
//                    }
//                });
                Intent mIntent = new Intent(LishiactivityActivity.this, EventdetailsActivity.class);
                mIntent.putExtra(KeyConstants.IntentPageValues.Actvites,mActivitesList.get(images2));
                startActivity(mIntent);
            }
        });
    }

    private void initData() {
        mActivitesList = new ArrayList<>();
        for (int i = 0;i < 5;i++){
            Activites mActivites = new Activites();
            mActivites.setActId(1111);
            mActivites.setActName("李氏活动");
            mActivites.setActLocation("北京市朝阳区");
            mActivites.setActScale("1000");
            mActivites.setActStatus(1);
            mActivites.setActShow(1);
            mActivites.setPic(R.drawable.activity1);
            mActivitesList.add(mActivites);

        }




        //创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, UrlConstants.GET_ALL_ACTIVITY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (JsonParseUtil.isSuccess(JsonParseUtil.getJsonByString(response).getAsJsonObject())){
                    QuickAdapter<Activites>  mQuickAdapter = new QuickAdapter<Activites>(LishiactivityActivity.this,R.layout.image_ativity,mActivitesList) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, Activites item) {
                            helper.setBackgroundRes(R.id.itemImage,item.getPic());
                        }
                    };
                    mLvActivity.setAdapter(mQuickAdapter);
                    //TODO 获得数据 展示到listview
                }else{
                    Toast.makeText(LishiactivityActivity.this,"获取数据失败",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("main",error.getMessage());
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
