package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.view.WheelDialog;


public class ShippingAddressActivity extends Activity implements OnClickListener{
    InputMethodManager manager;
    private EditText editText;
    private WheelDialog dialog;
    private TextView tvTitle;
    private ImageView imageback;

    /**
     * 定义存放省市区id的变量,用于添加收货地址
     */
    private String proId, cityId, areaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        //空白处隐藏软键盘
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText=(EditText)findViewById(R.id.iv_wheel);
        editText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog = new WheelDialog(ShippingAddressActivity.this, R.style.protocol_dialog, getAssets(), new WheelDialog.IRefreshUI() {
                    @Override
                    public void refresh(String info, String areaId) {
                        editText.setText(info);
                        ShippingAddressActivity.this.areaId = areaId;
                        System.out.println("areaId: "+ShippingAddressActivity.this.areaId);
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
    }
    protected void initView(){
        tvTitle.setText("收货地址");
        imageback.setOnClickListener(this);
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
          default:break;
      }
    }
}
