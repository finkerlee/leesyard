package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.bean.Resources;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.view.WheelDialog;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class ShippingAddressActivity extends BaseActivity implements OnClickListener{
    InputMethodManager manager;
    private EditText editTextaddress,editTextname,editTextphone,editTextdetailed;
    private WheelDialog dialog;
    private TextView tvTitle;
    private ImageView imageback;
    private Button btnsave;
    private SharedPreferences mSp;
    /**
     * 定义存放省市区id的变量,用于添加收货地址
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        mSp = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextaddress=(EditText)findViewById(R.id.et_address_wheel);
        editTextaddress.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog = new WheelDialog(ShippingAddressActivity.this, R.style.protocol_dialog, getAssets(), new WheelDialog.IRefreshUI() {
                    @Override
                    public void refresh(String info, String areaId) {
                        editTextaddress.setText(info);
                    }
                });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        findViewById();
        initView();
    }

    protected void findViewById() {
        tvTitle= (TextView) findViewById(R.id.text_title);
        imageback= (ImageView) findViewById(R.id.iv_back);
        btnsave= (Button) findViewById(R.id.btn_save);
        editTextname= (EditText) findViewById(R.id.et_name_address);
        editTextphone= (EditText) findViewById(R.id.et_phone_address);
        editTextdetailed= (EditText) findViewById(R.id.et_detailed_address);
    }
    protected void initView(){
        tvTitle.setText("收货地址");
        imageback.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        editTextaddress.setOnClickListener(this);
        editTextphone.setOnClickListener(this);
        editTextdetailed.setOnClickListener(this);
    }

    //空白处隐藏软键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void onClick(View v) {
      switch(v.getId()){
          case R.id.iv_back:
              finish();
              break;
          case R.id.btn_save:
              //判断收货人是否为空
              if(TextUtils.isEmpty(editTextname.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_name,Toast.LENGTH_SHORT).show();
              }
              //判断收货人手机号是否为空
              if(TextUtils.isEmpty(editTextphone.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_phone,Toast.LENGTH_SHORT).show();
              }
              //判断收货人地址是否为空
              if(TextUtils.isEmpty(editTextaddress.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_add,Toast.LENGTH_SHORT).show();
              }
              //判断详细地址是否为空
              if(TextUtils.isEmpty(editTextdetailed.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_detailed,Toast.LENGTH_SHORT).show();
              }
              // 创建请求队列
              RequestQueue mQueue = app.getRequestQueue();
              // 创建一个字符串请求
              StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.ADD_ADDRESS, new Response.Listener<String>() {
                  /** 重写onResponse,以实现请求响应的操作 **/
                  @Override
                  public void onResponse(String response) {
                      JsonParser mJsonParser = new JsonParser();
                      JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                      String result = json.get("response_status").getAsString();
                      if ("success".equals(result)) {
                          if (json.get("response_data").isJsonNull()){
                              Toast.makeText(ShippingAddressActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                          }else{
                              int data = json.get("response_data").getAsInt();
                              if (data == 0) {
                                  Toast.makeText(ShippingAddressActivity.this, "添加地址失败", Toast.LENGTH_SHORT).show();
                              } else if (data == 1) {
                                  Toast.makeText(ShippingAddressActivity.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(ShippingAddressActivity.this, LoginActivity.class);
                                  startActivity(intent);
                              }
                          }
                      }else{

                      }
                  }
              }, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      Log.i("main", error.toString());

                  }
              }){
                  /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError {
                      Map<String, String> params = new HashMap<>();
                      params.put("addId", "");
                      params.put("addName", editTextname.getText().toString().trim());
                      params.put("addPhone", editTextphone.getText().toString().trim());
                      params.put("addProvince", "");
                      params.put("addCity","");
                      params.put("addArea","");
                      params.put("addDetail ",editTextdetailed.getText().toString().trim());
                      params.put("userId", mSp.getString(KeyConstants.UserInfoKey.userId, ""));
                      return params;
                  }
              };
              // 将请求添加到请求队列中(即发送请求)
              mQueue.add(request);
          default:break;
      }
    }
}
