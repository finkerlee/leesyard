package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.http.UrlConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifanqiao on 16/3/11.
 */
public class ResetpasswordActivity extends BaseActivity implements View.OnClickListener{
    private TextView textback;
    private EditText pass1,pass2;
    private Button btnsure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        findViewById();
        initView();


    }
    protected void findViewById() {
        textback=(TextView)findViewById(R.id.et_loginback);
        pass1= (EditText) findViewById(R.id.et_reset_password);
        pass2= (EditText) findViewById(R.id.et_reset_password2);
        btnsure= (Button) findViewById(R.id.btn_sure);
    }
    protected void initView(){
        textback.setOnClickListener(this);
        pass1.setOnClickListener(this);
        pass2.setOnClickListener(this);
        btnsure.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_loginback:
                finish();
                break;
            case R.id.btn_sure:
                //验证两次密码是否一致
                if (!pass1.getText().toString().equals(pass2.getText().toString())){
                    Toast.makeText(ResetpasswordActivity.this,R.string.twice_password_no,Toast.LENGTH_SHORT).show();
                return;
                }
                Intent intent=getIntent();
                final String phonenum=intent.getStringExtra("phone");
                //创建队列请求
                RequestQueue mQueue=app.getRequestQueue();
                //创建一个字符串请求
                StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.FORGET_PASSWORD,new Response.Listener<String>() {
                    /** 重写onResponse,以实现请求响应的操作 **/
                    @Override
                    public void onResponse(String response) {
                        JsonParser mJsonParser = new JsonParser();
                        JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                        String result = json.get("response_status").getAsString();
                        if ("success".equals(result)) {
                            if (json.get("response_data").isJsonNull()){
                                Toast.makeText(ResetpasswordActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                            }else{
                                int data = json.get("response_data").getAsInt();
                                if (data == 0) {
                                    Toast.makeText(ResetpasswordActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                                } else if (data == 1) {
                                    Toast.makeText(ResetpasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ResetpasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }else{

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userPhone ", phonenum);
                        params.put("password", pass1.getText().toString().trim());
                        return params;
                    }
                };
                // 将请求添加到请求队列中(即发送请求)
                mQueue.add(request);

                break;
        }

    }

}
