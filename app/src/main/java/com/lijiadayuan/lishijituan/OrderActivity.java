package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class OrderActivity extends Activity {

    private RadioButton rbAli, rbWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        rbAli = (RadioButton) findViewById(R.id.rb_ali);
        rbWechat = (RadioButton) findViewById(R.id.rb_wechat);

//        rbAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    rbAli.setButtonDrawable(R.drawable.radio);
//                    rbWechat.setButtonDrawable(R.drawable.radio_off);
//                } else{
//                    rbAli.setButtonDrawable(R.drawable.radio_off);
//                    rbWechat.setButtonDrawable(R.drawable.radio);
//                }
//            }
//        });
//
//        rbWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    rbWechat.setButtonDrawable(R.drawable.radio);
//                    rbAli.setButtonDrawable(R.drawable.radio_off);
//                } else{
//                    rbWechat.setButtonDrawable(R.drawable.radio_off);
//                    rbAli.setButtonDrawable(R.drawable.radio);
//                }
//            }
//        });
    }
}
