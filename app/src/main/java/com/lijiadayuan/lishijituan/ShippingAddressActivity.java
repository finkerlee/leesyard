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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.bean.Addresses;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;
import com.lijiadayuan.lishijituan.utils.VerficationUtil;
import com.lijiadayuan.lishijituan.view.WheelDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类为编辑和添加新地址
 * 编辑 ＝1；
 * 添加 ＝ 0；
 *
 */
public class ShippingAddressActivity extends BaseActivity implements OnClickListener{
    public static final int ADD_ADDRESS = 0;
    public static final int UPDATA_ADDRESS = 1;

    private int currentType;
    private String url;

    InputMethodManager manager;
    private EditText editTextaddress,editTextname,editTextphone,editTextdetailed;
    private WheelDialog dialog;
    private TextView tvTitle;
    private Button btnsave;
    private Addresses address;
    private String mAddId;


    private String pageTypeText = "";
    /**
     * 定义存放省市区id的变量,用于添加收货地址
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        currentType = getIntent().getIntExtra("addressType",ADD_ADDRESS);
        editTextaddress=(EditText)findViewById(R.id.et_address_wheel);
        editTextaddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main","3333");
                dialog = new WheelDialog(ShippingAddressActivity.this, R.style.protocol_dialog, getAssets(), new WheelDialog.IRefreshUI() {
                    @Override
                    public void refresh(String info, String areaId) {
                        editTextaddress.setText(info);
                        mAddId = areaId;
                    }
                });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        initView();

    }
    private void setViewByData(Addresses address) {
        editTextaddress.setText(address.getAddArea());
        editTextname.setText(address.getAddName());
        editTextphone.setText(address.getAddPhone());
        editTextdetailed.setText(address.getAddDetail());
    }
    protected void initView(){
        tvTitle= (TextView) findViewById(R.id.text_title);

        findViewById(R.id.iv_back).setOnClickListener(this);
        btnsave= (Button) findViewById(R.id.btn_save);
        editTextname= (EditText) findViewById(R.id.et_name_address);
        editTextphone= (EditText) findViewById(R.id.et_phone_address);
        editTextdetailed= (EditText) findViewById(R.id.et_detailed_address);

        tvTitle.setText("收货地址");
        btnsave.setOnClickListener(this);
        editTextphone.setOnClickListener(this);
        editTextdetailed.setOnClickListener(this);

        if (currentType == UPDATA_ADDRESS){
            address = getIntent().getParcelableExtra("address");
            mAddId = address.getAddArea();
            setViewByData(address);
            url = UrlConstants.MODIFY_ADDRESS;
            pageTypeText = "修改";
        }else{
            mAddId = "";
            url = UrlConstants.ADD_ADDRESS;
            pageTypeText = "添加";
        }
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
                  return;
              }
              // 判断手机号是否为空  是否正确
              if (!VerficationUtil.checkMobile(this, editTextphone.getText().toString())) {
                  return;
              }
              //判断收货人地址是否为空
              if(TextUtils.isEmpty(editTextaddress.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_add,Toast.LENGTH_SHORT).show();
                  return;
              }
              //判断详细地址是否为空
              if(TextUtils.isEmpty(editTextdetailed.getText())){
                  Toast.makeText(ShippingAddressActivity.this, R.string.address_detailed,Toast.LENGTH_SHORT).show();
                  return;
              }
              // 创建请求队列
              RequestQueue mQueue = app.getRequestQueue();
              // 创建一个字符串请求
              StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                  /** 重写onResponse,以实现请求响应的操作 **/
                  @Override
                  public void onResponse(String response) {
                      JsonParser mJsonParser = new JsonParser();
                      JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                      String result = json.get("response_status").getAsString();
                      if ("success".equals(result)) {
                          if (json.get("response_data").isJsonNull()){
                              Toast.makeText(ShippingAddressActivity.this, pageTypeText + "失败", Toast.LENGTH_SHORT).show();
                          }else{
                              int data = json.get("response_data").getAsInt();
                              if (data == 0) {
                                  Toast.makeText(ShippingAddressActivity.this, pageTypeText +"地址失败", Toast.LENGTH_SHORT).show();
                              } else if (data == 1) {
                                  Toast.makeText(ShippingAddressActivity.this, pageTypeText +"地址成功", Toast.LENGTH_SHORT).show();
                                  setResult(RESULT_OK);
                                  finish();
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
                      params.put("addId",mAddId);
                      params.put("addName", editTextname.getText().toString().trim());
                      params.put("addPhone", editTextphone.getText().toString().trim());
                      params.put("addProvince", mAddId.substring(0,2));
                      params.put("addCity",mAddId.substring(0,4));
                      params.put("addArea",mAddId);
                      params.put("addDetail",editTextdetailed.getText().toString().trim());
                      if (currentType == ADD_ADDRESS){
                          params.put("userId", UsersUtil.getUserId(ShippingAddressActivity.this));
                      }
                      return params;
                  }
              };
              // 将请求添加到请求队列中(即发送请求)
              mQueue.add(request);
          default:break;
      }
    }
}
