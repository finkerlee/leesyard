package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.view.ProtocolDialog;

import org.w3c.dom.Text;

/**
 * Created by lifanqiao on 16/3/4.
 */

public class YonghuActivity extends Activity{
    private TextView text;
    private TextView textback;
    InputMethodManager manager;
    private ProtocolDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghu);
        textback=(TextView)findViewById(R.id.et_loginback);
        text=(TextView)findViewById(R.id.tx_XYuers);
        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        textback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.et_loginback:
//                        startActivity(new Intent(YonghuActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:break;
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dialog = new ProtocolDialog(YonghuActivity.this, R.style.protocol_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

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

}
