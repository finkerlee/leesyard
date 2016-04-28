package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lijiadayuan.lishijituan.bean.Users;
import com.lijiadayuan.lishijituan.http.UrlConstants;;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UpLoadImageTask;
import com.lijiadayuan.lishijituan.utils.UpLoadPicCallBack;
import com.lijiadayuan.lishijituan.view.photoscorrect;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout address,dimensional,member,welfare,join,mymessage,parent;
    private ImageView setting;
    private SimpleDraweeView headImage;
    private Users mUsers;
    private Boolean isLogin;
    private TextView mTvUserName,mTvUserLevel;
    private PopupWindow mPopupWindow;
    private photoscorrect dialog;

    protected SharedPreferences mSharedPreferences;


    public static final int HEAD_IMAGE = 100;

    //相册
    private static final int REQUESTCODE_PICK = 1;
    //相机
    private static final int REQUESTCODE_TAKE = 2;
    //裁减
    private static final int REQUESTCODE_CROP = 3;

    private static final String IMAGE_FILE_NAME = "lishijituan";

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
//        if (getIntent() != null){
//            mUsers = (Users) getIntent().getExtras().get(KeyConstants.UserInfoKey.userInfo);
//        }
        mSharedPreferences = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
        isLogin = mSharedPreferences.getBoolean(KeyConstants.UserInfoKey.userIsLogin, false);
        initView();

    }

    protected void initView() {
        dialog = new photoscorrect(this, R.style.protocol_dialog);
        parent = (RelativeLayout) findViewById(R.id.parent);
        address = (RelativeLayout)findViewById(R.id.ship_address);
        setting = (ImageView) findViewById(R.id.iv_setting);
        dimensional= (RelativeLayout) findViewById(R.id.iv_2D);
        member= (RelativeLayout) findViewById(R.id.iv_member);
        welfare= (RelativeLayout) findViewById(R.id.iv_welfare);
        join= (RelativeLayout) findViewById(R.id.iv_us);
        mymessage= (RelativeLayout) findViewById(R.id.iv_mymessage);
        headImage = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        mTvUserName = (TextView) findViewById(R.id.iv_name);
        mTvUserLevel = (TextView) findViewById(R.id.tv_user_level);

        findViewById(R.id.myorder).setOnClickListener(this);
        welfare.setOnClickListener(this);
        address.setOnClickListener(this);
        setting.setOnClickListener(this);
        dimensional.setOnClickListener(this);
        member.setOnClickListener(this);
        join.setOnClickListener(this);
        mymessage.setOnClickListener(this);
        headImage.setOnClickListener(this);

        if (isLogin){
            setViewByData();
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
//                    Toast.makeText(MineActivity.this, "修改头像", Toast.LENGTH_SHORT).show();
//                    showPopWindow();
                    dialog.show();
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                }else{
                    //跳转到登陆页面
                    Toast.makeText(MineActivity.this, "跳转到登陆页面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                    intent.putExtra(KeyConstants.IntentPageKey.ToLoginPageStyle,KeyConstants.IntentPageValues.forResult);
                    startActivityForResult(new Intent(MineActivity.this, LoginActivity.class), HEAD_IMAGE);
                }
                break;

            case R.id.tv_gallery:
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
                dialog.dismiss();
                break;
            case R.id.tv_photo:
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //下面这句指定调用相机拍照后的照片存储的路径
                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                dialog.dismiss();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.myorder:
                Intent intent = new Intent(this,MyOrderActivity.class);
                startActivity(intent);

//                Intent takeIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                //下面这句指定调用相机拍照后的照片存储的路径
//                takeIntent1.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
//                startActivityForResult(takeIntent1, REQUESTCODE_TAKE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("main", requestCode + "");
        if (requestCode == HEAD_IMAGE){
            if (resultCode == LoginActivity.LOGIN){
                isLogin = true;
                setViewByData();
            }
        }
        //相册
        if (requestCode == REQUESTCODE_PICK){
            startPhotoZoom(data.getData());
        }
        //相机
        if (requestCode == REQUESTCODE_TAKE){
            File temp = new File(Environment.getExternalStorageDirectory() + "/" + PHOTO_FILE_NAME);
            startPhotoZoom(Uri.fromFile(temp));
        }
        //裁减
        if (requestCode == REQUESTCODE_CROP){
            setPicToView(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViewByData() {
        mTvUserName.setText(mSharedPreferences.getString(KeyConstants.UserInfoKey.userNick,"默认"));
        mTvUserLevel.setText(mSharedPreferences.getString(KeyConstants.UserInfoKey.userLevel,"1"));
        String mHeadImage = mSharedPreferences.getString(KeyConstants.UserInfoKey.userHeadImage,"");
        if (!"".equals(mHeadImage)){
            headImage.setImageURI(Uri.parse(mHeadImage));
        }else{
            headImage.setBackgroundResource(R.drawable.user_normol_head_image);
        }
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    private void startPhotoZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CROP);
    }

    /**
     * 保存裁剪之后的图片数据  并且上传
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            final Bitmap photo = extras.getParcelable("data");

            UpLoadImageTask mTask = (UpLoadImageTask) new UpLoadImageTask(this,photo) .execute(UrlConstants.UPLOAD_AVATAR);
            mTask.setCallBACK(new UpLoadPicCallBack() {
                @Override
                public void setCompleteImage(ArrayList<String> iamgePicList) {

                    headImage.setImageURI(Uri.parse(iamgePicList.get(0)));
                    SharedPreferences.Editor mEditor =mSharedPreferences.edit();
                    mEditor.putString(KeyConstants.UserInfoKey.userHeadImage, iamgePicList.get(0));
                    mEditor.commit();
                    UpDataHeadImage(iamgePicList.get(0));
                }
            });

        }
    }

    /**
     * 修改头像
     */

    private void UpDataHeadImage(final String iamgePic) {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.AVATAR, new Response.Listener<String>() {
            /** 重写onResponse,以实现请求响应的操作 **/
            @Override
            public void onResponse(String response) {
                JsonParser mJsonParser = new JsonParser();
                JsonObject json = mJsonParser.parse(response).getAsJsonObject();
                String result = json.get("response_status").getAsString();
                if ("success".equals(result)){
                    if ("1".equals(json.get("response_data").getAsString())){
                        Toast.makeText(MineActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            /** 在StringRequest的匿名内部类中重写getParams方法,用于传递请求参数 **/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId",mSharedPreferences.getString(KeyConstants.UserInfoKey.userId,""));
                params.put("userAvatar", iamgePic);
                return params;
            }
        };
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);

    }




    //两次退出
    private static Boolean isQuit = false;
    Timer timer = new Timer();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                isQuit = true;
                Toast.makeText(getBaseContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return true;
    }

}

