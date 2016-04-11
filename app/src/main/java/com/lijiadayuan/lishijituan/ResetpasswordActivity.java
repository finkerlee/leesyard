package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lifanqiao on 16/3/11.
 */
public class ResetpasswordActivity extends Activity{
    private TextView textback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        textback=(TextView)findViewById(R.id.et_loginback);
        textback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.et_loginback:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
