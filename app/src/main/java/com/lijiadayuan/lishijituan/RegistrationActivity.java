package com.lijiadayuan.lishijituan;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.UpLoadPicAdapter;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.ArchivesUtils;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UpLoadImageTask1;
import com.lijiadayuan.lishijituan.utils.UpLoadPicCallBack;
import com.lijiadayuan.lishijituan.utils.UsersUtil;
import com.lijiadayuan.lishijituan.view.ReceiveDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends BaseActivity implements OnClickListener {

    //购买页面
    public static final int BUY_GOODS = 1;
    //赠品页面
    public static final int GIFT_GOODS = 0;
    //视图bean
    private ProductViewBean mProductViewBean;

    private final int OPEN_ALBUM_FLAG = 1023;
    private final int OPEN_CAMERA_FLAG = 1024;
    private TextView tvTitle;
    private ImageView imageback;
    InputMethodManager manager;
    private ReceiveDialog dialog;
    private static RegistrationActivity instance;
    private GridView mGridView;
    private ImageView mShowIV;
    private EditText mEtName,mEtSex,mEtNation,mEtPhoneNum;

    private int gender ;  // 0:女，1:男 整型

    private String mSaveDir;//拍照存放的文件夹名字
    private String mFileName;//拍照存放的文件的名字
    private String sex;

    private UpLoadPicAdapter mAdpter;
    private ArrayList<Bitmap> mBitmaps;
    private Bitmap[] bitmaps;

    private String strUserAge = "";
    //初始化选择器
    private OptionsPickerView pickerView;
    //商品id
    private String shoppingId;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        instance = this;
        shoppingId = getIntent().getStringExtra("shoppingId");
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mProductViewBean = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.productViewBeanType);
        String actId = mProductViewBean.getGoodsActivityId();
        Log.e("Log","actId===="+actId);

        initView();
        setListener();
    }

    private void setListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 4) {
                    return;
                } else if (i + 1 == mBitmaps.size()) {
                    dialog = new ReceiveDialog(RegistrationActivity.this, R.style.protocol_dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                } else {

                }
            }
        });

//        mEtSex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                pickerView.setTitle("选择性别");
//                pickerView.setPicker(ArchivesUtils.getAge());
//                pickerView.setCyclic(false);
//                if(!TextUtils.isEmpty(strUserAge)){
//                    pickerView.setSelectOptions(Integer.valueOf(strUserAge));
//                }
//                pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3) {
//                        mEtSex.setText(ArchivesUtils.getAge().get(options1));
////                        strUserAge = ArchivesUtils.getAgeOp(ArchivesUtils.getAge().get(options1));
////                        refreshMenuView();
//                    }
//                });
//                if(!pickerView.isShowing()){
//                    pickerView.show();
//                }
//            }
//        });
        mEtSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.hasFocus()){
                    //显示自己界面
                    pickerView.setTitle("选择性别");
                    pickerView.setPicker(ArchivesUtils.getAge());
                    pickerView.setCyclic(false);
                    if(!TextUtils.isEmpty(strUserAge)){
                        pickerView.setSelectOptions(Integer.valueOf(strUserAge));
                    }
                    pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3) {
                            mEtSex.setText(ArchivesUtils.getAge().get(options1));
//                        strUserAge = ArchivesUtils.getAgeOp(ArchivesUtils.getAge().get(options1));
//                        refreshMenuView();
                        }
                    });
                    if(!pickerView.isShowing()){
                        pickerView.show();
                    }
                }
            }
        });

//        mEtNation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                pickerView.setTitle("选择民族");
//                pickerView.setPicker(ArchivesUtils.getNation());
//                pickerView.setCyclic(false);
//                if(!TextUtils.isEmpty(strUserAge)){
//                    pickerView.setSelectOptions(Integer.valueOf(strUserAge));
//                }
//                pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3) {
//                        mEtNation.setText(ArchivesUtils.getNation().get(options1));
////                        strUserAge = ArchivesUtils.getAgeOp(ArchivesUtils.getAge().get(options1));
////                        refreshMenuView();
//                    }
//                });
//                if(!pickerView.isShowing()){
//                    pickerView.show();
//                }
//            }
//        });
        mEtNation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.setTitle("选择民族");
                pickerView.setPicker(ArchivesUtils.getNation());
                pickerView.setCyclic(false);
                if(!TextUtils.isEmpty(strUserAge)){
                    pickerView.setSelectOptions(Integer.valueOf(strUserAge));
                }
                pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        mEtNation.setText(ArchivesUtils.getNation().get(options1));
//                        strUserAge = ArchivesUtils.getAgeOp(ArchivesUtils.getAge().get(options1));
//                        refreshMenuView();
                    }
                });
                if(!pickerView.isShowing()){
                    pickerView.show();
                }
            }
        });
    }

    protected void initView() {
        pickerView = new OptionsPickerView(this);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        mShowIV = (ImageView) findViewById(R.id.iv_photos);
        mGridView = (GridView) findViewById(R.id.up_load_pic);
        mEtName = (EditText) findViewById(R.id.name);
        mEtSex = (EditText) findViewById(R.id.sex);
        mEtSex.setInputType(InputType.TYPE_NULL);


        mEtNation = (EditText) findViewById(R.id.nation);//
        mEtNation.setInputType(InputType.TYPE_NULL);
        mEtPhoneNum = (EditText) findViewById(R.id.phone_num);

        findViewById(R.id.btn_receive).setOnClickListener(this);
        tvTitle.setText("活动申请报名");
        imageback.setOnClickListener(this);
        mBitmaps = new ArrayList<>();
        Bitmap defaultPic = BitmapFactory.decodeResource(getResources(), R.drawable.upload);
        mBitmaps.add(defaultPic);
        mAdpter = new UpLoadPicAdapter(this, mBitmaps);
        mGridView.setAdapter(mAdpter);

    }

    //空白处隐藏软键盘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    public static RegistrationActivity getInstance() {
        return instance;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File file;
        Bitmap bitmap;
        OutputStream outputStream;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OPEN_ALBUM_FLAG://从相册获取回调
                    Uri originUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(originUri, proj, null, null, null);
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(columnIndex);
                    bitmap = getCompressBitmap(path);
                    mBitmaps.add(0, bitmap);
                    mAdpter.UpDataView(mBitmaps);
                    //将选中的图片展示出来
                /* 创建一个新的文件，存放压缩过的bitmap，用于发送给服务器 */
                    String saveDir = Environment.getExternalStorageDirectory() + "/wyk_dir/";
                    File dir = new File(saveDir);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    //指定拍取照片的名字(以时间戳命名，避免重复)
                    String fileName = "tmp.jpg";
                    file = new File(saveDir, fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        file.createNewFile();
                        outputStream = new FileOutputStream(file);
                        //将bitmap写入file
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        System.out.println("file size:" + file.length());
                    /* 到这里已经成功将bitmap写入file了，此时可以将file或者流发送给服务器了 */
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case OPEN_CAMERA_FLAG://拍照获取的回调
                    file = new File(mSaveDir + mFileName);//拍照前指定的输出路径
                    bitmap = getCompressBitmap(mSaveDir + mFileName);
                    mBitmaps.add(0, bitmap);
                    mShowIV.setImageBitmap(bitmap);//让拍照的照片显示在控件上
                    try {
                        outputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        System.out.println("file size:" + file.length());
                    /* 到这里已经成功将bitmap写入file了，此时可以将file或者流发送给服务器了 */
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 一般相册或拍照的图片都很大，不会直接传给服务器，所以需要压缩
     *
     * @param path 图片的路径
     * @return 压缩后的图片
     */
    private Bitmap getCompressBitmap(String path) {
        int BASE_SIZE = 100;//需要压缩到的最小宽高
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只计算几何尺寸不返回bitmap，这样不会占用内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int width = options.outWidth;
        int height = options.outHeight;
        int min = width < height ? width : height;
        int rate = min / BASE_SIZE;//压缩倍率
        if (rate < 1) rate = 1;
        options.inSampleSize = rate;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
            case R.id.tv_gallery://相册获取
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_ALBUM_FLAG);
                dialog.dismiss();
                break;
            case R.id.tv_photo://拍照获取
                //指定拍照后的输出路径
                mSaveDir = Environment.getExternalStorageDirectory() + "/wyk_dir/";
                File dir = new File(mSaveDir);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //指定拍取照片的名字(以时间戳命名，避免重复)
                mFileName = "WYK" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                File file = new File(mSaveDir, mFileName);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, OPEN_CAMERA_FLAG);
                dialog.dismiss();
                break;
            case R.id.btn_receive:


                ;
                sex = mEtSex.getText().toString();
                if("女".equals(sex)  ){
                    gender = 0;
                }else if ("男".equals(sex)){
                    gender = 1;
                }else{
                    Toast.makeText(RegistrationActivity.this,"性别填写有误",Toast.LENGTH_SHORT).show();
                }
                Log.e("Log", "sex  =======" + sex);



                if (mEtName.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"请填写申请人姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtSex.getText().toString().isEmpty()){

                    Toast.makeText(RegistrationActivity.this,"请填写性别",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtNation.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"请填写民族",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtPhoneNum.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"请填写手机号",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mBitmaps.size() == 1){
                    Toast.makeText(RegistrationActivity.this,"请上传图片",Toast.LENGTH_LONG).show();
                    return;
                }
                bitmaps = new Bitmap[mBitmaps.size()-1];
                for (int i = 0; i<mBitmaps.size()-1; i++){
                    bitmaps[i] = mBitmaps.get(i);
                }
                UpLoadImageTask1 mUpLoadImageTask1 = (UpLoadImageTask1) new UpLoadImageTask1(RegistrationActivity.this,bitmaps).execute(UrlConstants.UP_LOAD_PIC);
                mUpLoadImageTask1.setCallBACK(new UpLoadPicCallBack() {
                    @Override
                    public void setCompleteImage(final ArrayList<String> iamgePicList) {
                        if (iamgePicList.size()>0){
                            RequestQueue mRequestQueue = app.getRequestQueue();
                            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.ACTIVITY, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("log","response === " + response);
                                    JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                    if (JsonParseUtil.isSuccess(mJsonObj)){
                                        Toast.makeText(RegistrationActivity.this,"ok",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("log","error === " + error);
                                    Toast.makeText(RegistrationActivity.this,"faile",Toast.LENGTH_LONG).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("actId", "1");//活动id
                                    params.put("userId",UsersUtil.getUserId(RegistrationActivity.this));//用户id
                                    params.put("acaName",mEtName.getText().toString());//姓名
                                    params.put("acaGender",gender + "");//性别 1
                                    params.put("acaNation",mEtNation.getText().toString());//民族
                                    params.put("acaPhone",mEtPhoneNum.getText().toString() );//手机号
                                    if (iamgePicList.size()>0){
                                        for (int i = 0;i<iamgePicList.size();i++){
                                            params.put("acaImg"+(i+1),iamgePicList.get(i));
                                        }
                                    }
                                    return params;
                                }
                            };
                            mRequestQueue.add(mStringRequest);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
