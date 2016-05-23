package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;


public class SettingActivity extends BaseActivity implements OnClickListener {
    private TextView tvTitle, about, law,wechat,mTvShare;
    private SharedPreferences mSp;
    private IWXAPI weiXinApi;
    private LinearLayout layoutShare;
    private Button mBtnFriend,mBtnFriendCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        weiXinApi = WXAPIFactory.createWXAPI(this, OrderActivity.APP_ID);
        mSp = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
        initView();
    }


    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        about = (TextView) findViewById(R.id.iv_about);
        //law = (TextView) findViewById(R.id.iv_law);
        wechat= (TextView) findViewById(R.id.iv_wechat);
        mTvShare = (TextView) findViewById(R.id.share_friend);
        layoutShare = (LinearLayout) findViewById(R.id.layout_share);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.cancle).setOnClickListener(this);
        findViewById(R.id.friend_circle).setOnClickListener(this);
        findViewById(R.id.friend).setOnClickListener(this);
        tvTitle.setText("设置");
        about.setOnClickListener(this);
        //law.setOnClickListener(this);
        wechat.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
//            case R.id.iv_law:
//                startActivity(new Intent(SettingActivity.this, LawActivity.class));
//                break;
            case R.id.iv_wechat:
                startActivity(new Intent(SettingActivity.this, WechatActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.cancle:
                SharedPreferences.Editor mEditor = mSp.edit();
                mEditor.clear();
                mEditor.commit();
                Toast.makeText(SettingActivity.this,"注销成功",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.share_friend:
                layoutShare.setVisibility(View.VISIBLE);

                break;

            case R.id.friend:
                share2weixin(SendMessageToWX.Req.WXSceneSession);
                layoutShare.setVisibility(View.GONE);
                break;
            case R.id.friend_circle:
                share2weixin(SendMessageToWX.Req.WXSceneTimeline);
                layoutShare.setVisibility(View.GONE);
                break;
        }
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void share2weixin(int flag) {
        if (!weiXinApi.isWXAppInstalled()) {
            Toast.makeText(SettingActivity.this, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "title";
        msg.description = "能分享么";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        Boolean b = weiXinApi.sendReq(req);
        Log.i("main",b + "分享");
    }
}
