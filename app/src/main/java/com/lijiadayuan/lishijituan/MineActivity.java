package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MineActivity extends Activity implements View.OnClickListener {
    private RelativeLayout address,dimensional,member,welfare,join,mymessage;
    private ImageView setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        findViewById();
        initView();
    }

    protected void initView() {
        welfare.setOnClickListener(this);
        address.setOnClickListener(this);
        setting.setOnClickListener(this);
        dimensional.setOnClickListener(this);
        member.setOnClickListener(this);
        join.setOnClickListener(this);
        mymessage.setOnClickListener(this);
    }
    protected void findViewById() {
        // TODO Auto-generated method stub
        address = (RelativeLayout)findViewById(R.id.ship_address);
        setting = (ImageView) findViewById(R.id.iv_setting);
        dimensional= (RelativeLayout) findViewById(R.id.iv_2D);
        member= (RelativeLayout) findViewById(R.id.iv_member);
        welfare= (RelativeLayout) findViewById(R.id.iv_welfare);
        join= (RelativeLayout) findViewById(R.id.iv_us);
        mymessage= (RelativeLayout) findViewById(R.id.iv_mymessage);
 }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button:
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                break;
            case R.id.iv_setting:
                startActivity(new Intent(MineActivity.this,SettingActivity.class));
                break;
            case R.id.ship_address:
                startActivity(new Intent(MineActivity.this,AddressActivity.class));
                break;
            case R.id.iv_2D:
                startActivity(new Intent(MineActivity.this,TwodActivity.class));
                break;
            case R.id.iv_member:
                startActivity(new Intent(MineActivity.this,MemberActivity.class));
                break;
            case  R.id.iv_welfare:
                startActivity(new Intent(MineActivity.this,MywelfareActivity.class));
                break;
            case R.id.iv_us:
                startActivity(new Intent(MineActivity.this,JoinusActivity.class));
                break;
            case R.id.iv_mymessage:
                startActivity(new Intent(MineActivity.this,MymessageActivity.class));
                break;
            default:
                break;
        }
    }
}

