package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.view.CircleTextImageView;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout address,dimensional,member,welfare,join,mymessage;
    private ImageView setting;
    private SimpleDraweeView headImage;
    private Users mUsers;
    private Boolean isLogin;
    private TextView mTvUserName,mTvUserLevel;

    public static final int HEAD_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
//        if (getIntent() != null){
//            mUsers = (Users) getIntent().getExtras().get(KeyConstants.UserInfoKey.userInfo);
//        }
        isLogin = mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
        initView();

    }

    protected void initView() {

        address = (RelativeLayout)findViewById(R.id.ship_address);
        setting = (ImageView) findViewById(R.id.iv_setting);
        dimensional= (RelativeLayout) findViewById(R.id.iv_2D);
        member= (RelativeLayout) findViewById(R.id.iv_member);
        welfare= (RelativeLayout) findViewById(R.id.iv_welfare);
        join= (RelativeLayout) findViewById(R.id.iv_us);
        mymessage= (RelativeLayout) findViewById(R.id.iv_mymessage);
        headImage = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        mTvUserName = (TextView) findViewById(R.id.iv_name);
        //mTvUserLevel = (TextView) findViewById(R.id.user_level);


        welfare.setOnClickListener(this);
        address.setOnClickListener(this);
        setting.setOnClickListener(this);
        dimensional.setOnClickListener(this);
        member.setOnClickListener(this);
        join.setOnClickListener(this);
        mymessage.setOnClickListener(this);
        headImage.setOnClickListener(this);

        if (isLogin){
            String headImagePath = mSharedPreferences.getString(KeyConstants.UserInfoKey.userHeadImage, "");
            Log.i("main",headImagePath);
            if ("".equals(headImagePath)){
                headImage.setBackgroundResource(R.drawable.user_normol_head_image);
            }else{
                headImage.setImageURI(Uri.parse(headImagePath));
            }
        }else{
            headImage.setBackgroundResource(R.drawable.user_normol_head_image);
        }
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
            case R.id.iv_avatar:
                if (isLogin){
                    //修改头像
                    Toast.makeText(MineActivity.this, "修改头像", Toast.LENGTH_SHORT).show();

                }else{
                    //跳转到登陆页面
                    Toast.makeText(MineActivity.this, "跳转到登陆页面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                    intent.putExtra(KeyConstants.IntentPageKey.ToLoginPageStyle,KeyConstants.IntentPageValues.forResult);
                    startActivityForResult(new Intent(MineActivity.this, LoginActivity.class), HEAD_IMAGE);
                }
                break;
            default:
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("main",requestCode+"");
        if (resultCode == HEAD_IMAGE){
            isLogin = true;
            mUsers = (Users) data.getExtras().get(KeyConstants.UserInfoKey.userInfo);
            setViewByData(mUsers);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViewByData(Users mUsers){
        mTvUserName.setText(mUsers.getUserName());
        //mTvUserLevel.setText(mUsers.getUserLevel());
        if (!"".equals(mUsers.getUserAvatar())){
            headImage.setImageURI(Uri.parse(mUsers.getUserAvatar()));
        }else{
            headImage.setBackgroundResource(R.drawable.user_normol_head_image);
        }
    }
}

