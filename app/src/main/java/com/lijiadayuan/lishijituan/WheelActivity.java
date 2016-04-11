package com.lijiadayuan.lishijituan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.widget.OnWheelChangedListener;
import com.lijiadayuan.widget.WheelView;
import com.lijiadayuan.widget.adapters.ArrayWheelAdapter;

public class WheelActivity extends BaseActivity {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView medtConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
//        setUpViews();
//        setUpListener();
//        setUpData();
    }



}
