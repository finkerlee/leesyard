package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.bean.Addresses;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton rbAli, rbWechat;
    private TextView mNameTV; // 收货人姓名
    private TextView mPhoneTV; // 手机号
    private TextView mAddressTV; // 收货地址

    private ImageView iv_order_img;//商品图片
    private TextView tv_order_name;//商品名字
    private TextView tv_order_price;//商品价钱
    private TextView tv_order_bela;//
    private RelativeLayout rl_order_addresslayout; // 地址信息的布局

    //视图bean
    private ProductViewBean mProductViewBean; // 从上一个界面得到的商品数据
    private List<Addresses> mAddressList; // 获取的收货地址列表
    private Addresses mAddress; // 当前收货地址
    private String userId;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public OrderActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        rbAli = (RadioButton) findViewById(R.id.rb_ali);
        rbWechat = (RadioButton) findViewById(R.id.rb_wechat);
        mNameTV = (TextView) findViewById(R.id.iv_name);
        mPhoneTV = (TextView) findViewById(R.id.iv_iphone);
        mAddressTV = (TextView) findViewById(R.id.tv_address);

        iv_order_img = (ImageView) findViewById(R.id.iv_order_img);
        tv_order_name = (TextView) findViewById(R.id.tv_order_name);
        tv_order_price = (TextView) findViewById(R.id.tv_order_price);
        tv_order_bela = (TextView) findViewById(R.id.tv_order_bela);
        rl_order_addresslayout = (RelativeLayout) findViewById(R.id.rl_order_addresslayout);
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        userId = mSharedPreferences.getString(KeyConstants.UserInfoKey.userId, "");

        rl_order_addresslayout.setOnClickListener(this);

        getAddressViewBean();

                rbAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            rbAli.setBackgroundResource(R.drawable.radio_);
                            rbWechat.setBackgroundResource(R.drawable.radio_off);
                        } else{
                            rbAli.setBackgroundResource(R.drawable.radio_off);
                            rbWechat.setBackgroundResource(R.drawable.radio_);
                        }
                    }
                });
                rbWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            rbWechat.setBackgroundResource(R.drawable.radio_);
                            rbAli.setBackgroundResource(R.drawable.radio_off);
                        } else{
                            rbWechat.setBackgroundResource(R.drawable.radio_off);
                            rbAli.setBackgroundResource(R.drawable.radio_);
                        }
                    }
                });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 获取收货地址列表
     */
    private void getAddressViewBean() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId + "");

        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, getUrl(UrlConstants.GET_ADDRESS,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)) {
                    mAddressList = JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(), Addresses.class);
                    setAddressView();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);
    }

    /**
     * 拼接url
     * @return
     */
    private java.lang.String getUrl(String url, Map<String, String> params) {
        if(params.size() == 0) {
            return url;
        }

        url += "?";
        for(Map.Entry<String, String> entry : params.entrySet()) {
            url += entry.getKey() + "=" + entry.getValue() + "&";
        }

        return url;
    }

    /**
     * 设置地址视图
     */
    private void setAddressView() {
        if (mAddressList == null || mAddressList.size() == 0) {
            Toast.makeText(this, "暂时没有收货地址", Toast.LENGTH_SHORT).show();
        } else {
            mAddress = mAddressList.get(0);
            mNameTV.setText(mAddress.getAddName());
            mPhoneTV.setText(mAddress.getAddPhone());
            mAddressTV.setText(mAddress.getAddProvince() + mAddress.getAddCity() + mAddress.getAddArea() + mAddress.getAddDetail());
        }
        setProductView();
    }

    /**
     * 设置商品视图
     */

    private void setProductView() {
        if (mProductViewBean == null) {
            Toast.makeText(this, "获取商品数据失败！", Toast.LENGTH_SHORT).show();
            return;
        }

        iv_order_img.setImageURI(Uri.parse(mProductViewBean.getGoodsInfoUrl()));
        tv_order_name.setText(mProductViewBean.getGoodsName());
        tv_order_price.setText(mProductViewBean.getGoodsPrice());
        tv_order_bela.setText(mProductViewBean.getGoodsSpec());//???
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.lijiadayuan.lishijituan/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.lijiadayuan.lishijituan/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_order_addresslayout:
                Intent intent = new Intent(this, AddressActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
