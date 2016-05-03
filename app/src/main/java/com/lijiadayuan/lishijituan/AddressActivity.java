package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.Addresses;
import com.lijiadayuan.lishijituan.bean.Celebrities;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.model.CityModel;
import com.lijiadayuan.model.DistrictModel;
import com.lijiadayuan.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;


public class AddressActivity extends BaseActivity implements OnClickListener {
    private ListView catergory_listview;
    private Button button;
    private TextView tvTitle;
    private ImageView imageback;
    ArrayList<Addresses> AList;
    ArrayList<CityModel> cityList;
    ArrayList<DistrictModel> districtList;
    ArrayList<ProvinceModel> provinceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        findViewById();
        initView();
    }

    private void findViewById() {
        button = (Button) findViewById(R.id.id_address);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        catergory_listview = (ListView) this.findViewById(R.id.address_listview);
    }
    private void initView(){
        button.setOnClickListener(this);
        tvTitle.setText("地址管理");
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
                    AList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Addresses.class);
                    QuickAdapter<Addresses> adapter=new QuickAdapter<Addresses>(AddressActivity.this,R.layout.activity_address_item,AList) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, Addresses item) {
                            helper.setText(R.id.iv_name, item.getAddName());
                            helper.setText(R.id.iv_iphone,item.getAddPhone());
                            helper.setText(R.id.iv_address,item.getAddProvince()+item.getAddCity());
                            helper.getView().findViewById(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent mIntent = new Intent(AddressActivity.this,ShippingAddressActivity.class);
                                }
                            });
                        }
                    };
                    catergory_listview.setAdapter(adapter);
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
            case R.id.id_address:
                startActivity(new Intent(this, ShippingAddressActivity.class));
                break;
            default:break;
        }
    }
}
