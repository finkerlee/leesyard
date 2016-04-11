package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private TextView register;
    private TextView forgetpass;
    InputMethodManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register =(TextView)findViewById(R.id.et_yonghu);
        forgetpass=(TextView)findViewById(R.id.et_pass);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.et_yonghu:
                        startActivity(new Intent(LoginActivity.this, YonghuActivity.class));
                        break;
                    case R.id.et_pass:
                        startActivity(new Intent(LoginActivity.this, ForgetpasswordActivtiy.class));
                        break;
                    default:
                        break;
                }
            }
        });
//        forgetpass=(TextView)findViewById(R.id.et_pass);
//        forgetpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.et_pass:
//                        startActivity(new Intent(LoginActivity.this, ForgetpasswordActivtiy.class));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }






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
}
