package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.map.Text;
import com.facebook.drawee.view.SimpleDraweeView;
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

public class OrderActivity extends BaseActivity implements View.OnClickListener{

    //联系人信息
    private TextView mNameTV; // 收货人姓名
    private TextView mPhoneTV; // 手机号
    private TextView mAddressTV; // 收货地址

    //商品图片的显示
    private SimpleDraweeView mIvPic;
    //购买的布局
    private LinearLayout mLayoutBuy;
    private TextView mTvBuyGoodsName;
    private TextView mTvBuyGoodsOtherName;
    private TextView mTvBuyGoodsPrice;
    private TextView mTvBuyGoodsNum;
    private TextView mTvAdd,mTvReduce;
    //赠送的布局
    private LinearLayout mLayoutGive;
    private TextView mTvGiveGoodsName;
    private TextView mTvGiveGoodsOtherName;
    private TextView mTvGiveGoodsNum;
    private TextView mTvGiveGoodsPrice;
    //配送方式布局
    private TextView mTvExpressStyle;
    private TextView mTvExPressPrice;
    //支付方式
    private RadioButton mRbWeiXin,mRbAli;

    private Button mBtnCancle,mBtnSure;

    // 创建请求队列
    private RequestQueue mQueue;
    //商品数量
    private int mGoodsNum = 1;
    //商品总价
    private double mPrice = 0.00;

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
        setContentView(R.layout.activity_order1);
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);
        SharedPreferences mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        userId = mSharedPreferences.getString(KeyConstants.UserInfoKey.userId, "");
        //初始化布局
        initView();
        //初始化数据
        initData();









        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //1，加载地址列表数据
        getAddressViewBean();
        //2,根据前面穿过来的数据去加载view
        setProductView();

    }

    private void initView() {
        //联系人信息
        mNameTV = (TextView) findViewById(R.id.tv_name);
        mPhoneTV = (TextView) findViewById(R.id.tv_name);
        mAddressTV = (TextView) findViewById(R.id.tv_name);
        //购买的布局
        mLayoutBuy = (LinearLayout) findViewById(R.id.buy_layout);
        mTvBuyGoodsName = (TextView) findViewById(R.id.buy_goods_name);
        mTvBuyGoodsOtherName = (TextView) findViewById(R.id.buy_english_name);
        mTvBuyGoodsPrice = (TextView) findViewById(R.id.buy_goods_price);
        mTvBuyGoodsNum = (TextView) findViewById(R.id.buy_goods_num);
        mTvAdd = (TextView) findViewById(R.id.jia);
        mTvReduce = (TextView) findViewById(R.id.jian);

        //赠送的布局
        mLayoutGive = (LinearLayout) findViewById(R.id.give_layout);
        mTvGiveGoodsName = (TextView) findViewById(R.id.give_goods_name);
        mTvGiveGoodsOtherName = (TextView) findViewById(R.id.give_english_name);
        mTvGiveGoodsNum = (TextView) findViewById(R.id.give_goods_num);
        mTvGiveGoodsPrice = (TextView) findViewById(R.id.give_goods_price);
        //配送的布局
        mTvExpressStyle = (TextView) findViewById(R.id.express_style);
        mTvExPressPrice = (TextView) findViewById(R.id.express_price);
        //支付方式
        mRbWeiXin = (RadioButton) findViewById(R.id.rb_wechat);
        mRbAli = (RadioButton) findViewById(R.id.rb_ali);

        mBtnCancle = (Button) findViewById(R.id.cancle);
        mBtnSure = (Button) findViewById(R.id.be_sure);

        mBtnCancle.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        findViewById(R.id.rl_order_addresslayout).setOnClickListener(this);
    }

    /**
     * 获取收货地址列表
     */
    private void getAddressViewBean() {
        // 创建请求队列
        mQueue = app.getRequestQueue();

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId + "");

        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, getUrl(UrlConstants.USER_ADDRESS,params), new Response.Listener<String>() {
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

    }

    /**
     * 设置商品视图
     */
    private void setProductView() {
        if (mProductViewBean == null) {
            Toast.makeText(this, "获取商品数据失败！", Toast.LENGTH_SHORT).show();
            return;
        }
        //设置商品图片
        if ("".equals(mProductViewBean.getGoodsPic())){
            mIvPic.setImageURI(Uri.parse("res://com.lijiadayuan.lishijituan/" + R.drawable.user_normol_head_image));
        }else{
            mIvPic.setImageURI(Uri.parse(mProductViewBean.getGoodsPic()));
        }
        //根据当前的模式 设置商品信息
        if (mProductViewBean.getGoodsType() == ProductBaseActivity.GIFT_GOODS){
            mLayoutBuy.setVisibility(View.GONE);
            mLayoutGive.setVisibility(View.VISIBLE);
            mTvGiveGoodsName.setText(mProductViewBean.getGoodsName());
            mTvGiveGoodsOtherName.setText(mProductViewBean.getGoodsOtherName());
            mTvGiveGoodsPrice.setText("￥" + mProductViewBean.getGoodsPrice());
            mTvGiveGoodsNum.setText(mProductViewBean.getGoodsNum());
            mTvExpressStyle.setText("快递默认为在线支付");

        }else if(mProductViewBean.getGoodsType() == ProductBaseActivity.BUY_GOODS){
            mLayoutBuy.setVisibility(View.VISIBLE);
            mLayoutGive.setVisibility(View.GONE);
            mTvBuyGoodsName.setText(mProductViewBean.getGoodsName());
            mTvBuyGoodsOtherName.setText(mProductViewBean.getGoodsOtherName());
            mTvBuyGoodsPrice.setText("￥" + mProductViewBean.getGoodsPrice());
            mTvBuyGoodsNum.setText(mProductViewBean.getGoodsNum());
            mTvExpressStyle.setText("快递默认为货到付款");
        }




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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancle:
                finish();
                break;
            case R.id.be_sure:
                account();
                break;
            case R.id.jia:
                if (mGoodsNum < mProductViewBean.getGoodsStock()){
                    mTvBuyGoodsNum.setText(mGoodsNum++);
                }
                break;
            case R.id.jian:
                if (mGoodsNum > 1){
                    mTvBuyGoodsNum.setText(mGoodsNum--);
                }
                break;
            case R.id.rl_order_addresslayout:
                break;
        }
    }

    /**
     * 1,请求后台服务  下单
     * 2,当下单请求回来后 调用三方结算sdk
     * 3,当sdk请求结束后调用  添加支付／完成日期 接口
     */
    private void account() {
        if (mProductViewBean.getGoodsType() == ProductBaseActivity.GIFT_GOODS){
            //当前商品是赠品的时候，只需要出运费,数量只能为1
            mGoodsNum =  1;
            mPrice = Double.parseDouble(mProductViewBean.getGoodsPrice());

        }else if(mProductViewBean.getGoodsType() == ProductBaseActivity.BUY_GOODS){
            //当前商品是购买的时候，运费到付，在线支付商品的价格，数量能变
            mGoodsNum = Integer.parseInt(mTvBuyGoodsNum.getText().toString());
            mPrice = Double.parseDouble(mProductViewBean.getGoodsPrice())*mGoodsNum;
        }
        StringRequest mAccountRequest = new StringRequest(Request.Method.POST, UrlConstants.ORDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                        if (JsonParseUtil.isSuccess(mJsonObject)){

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
                params.put("proId",mProductViewBean.getGoodsId());
                params.put("userId",userId);
                params.put("addId",mAddress.getAddId());
                params.put("count",mGoodsNum+"");
                params.put("amount",mPrice+"");
                return params;
            }
        };
        mQueue.add(mAccountRequest);
    }
}
