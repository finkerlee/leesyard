package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lifanqiao on 16/3/11.
 */
public class ForgetpasswordActivtiy extends Activity {
    private TextView textback;
    private TextView resetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_forget_password);
        textback=(TextView)findViewById(R.id.et_loginback);
        textback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.et_loginback:
                        finish();
                        break;
                    default:break;
                }
            }
        });
        resetpassword=(TextView)findViewById(R.id.et_Verification);
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.et_Verification:
                        startActivity(new Intent(ForgetpasswordActivtiy.this, ResetpasswordActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
