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
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.UpLoadPicAdapter;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
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

public class SubmitDataActivity extends BaseActivity implements OnClickListener {
    private final int OPEN_ALBUM_FLAG = 1023;
    private final int OPEN_CAMERA_FLAG = 1024;
    private TextView tvTitle;
    InputMethodManager manager;
    private ReceiveDialog dialog;
    private static SubmitDataActivity instance;
    private GridView mGridView;
    private ImageView mShowIV;
    private EditText mEtName,mEtPhone,mEtAddress,mEtSituation;

    private String mSaveDir;//拍照存放的文件夹名字
    private String mFileName;//拍照存放的文件的名字

    private UpLoadPicAdapter mAdpter;
    private ArrayList<Bitmap> mBitmaps;
    private Bitmap[] bitmaps;
    //商品id
    private String shoppingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_data);
        instance = this;
        shoppingId = getIntent().getStringExtra("shoppingId");
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        setListener();
    }

    private void setListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 4) {
                    return;
                } else if (i+1 == mBitmaps.size()) {
                    dialog = new ReceiveDialog(SubmitDataActivity.this, R.style.protocol_dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                } else {

                }
            }
        });

    }

    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);

        findViewById(R.id.iv_back).setOnClickListener(this);
        mShowIV = (ImageView) findViewById(R.id.iv_photos);
        mGridView = (GridView) findViewById(R.id.up_load_pic);
        mEtName = (EditText) findViewById(R.id.name);
        mEtPhone = (EditText) findViewById(R.id.phone);
        mEtAddress = (EditText) findViewById(R.id.address);
        mEtSituation = (EditText) findViewById(R.id.situation);
        findViewById(R.id.btn_receive).setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        tvTitle.setText("我要领取");
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

    public static SubmitDataActivity getInstance() {
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
                if (mEtName.getText().toString().isEmpty()){
                    Toast.makeText(SubmitDataActivity.this,"请填写申请人姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtPhone.getText().toString().isEmpty()){
                    Toast.makeText(SubmitDataActivity.this,"请填写联系方式",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtAddress.getText().toString().isEmpty()){
                    Toast.makeText(SubmitDataActivity.this,"请填写收货地址",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtSituation.getText().toString().isEmpty()){
                    Toast.makeText(SubmitDataActivity.this,"请填写情况说明",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mBitmaps.size() == 1){
                    Toast.makeText(SubmitDataActivity.this,"请上传图片",Toast.LENGTH_LONG).show();
                    return;
                }
                bitmaps = new Bitmap[mBitmaps.size()-1];
                for (int i = 0; i<mBitmaps.size()-1; i++){
                    bitmaps[i] = mBitmaps.get(i);
                }
                UpLoadImageTask1 mUpLoadImageTask1 = (UpLoadImageTask1) new UpLoadImageTask1(SubmitDataActivity.this,bitmaps).execute(UrlConstants.UP_LOAD_PIC);
                mUpLoadImageTask1.setCallBACK(new UpLoadPicCallBack() {
                    @Override
                    public void setCompleteImage(final ArrayList<String> iamgePicList) {
                        if (iamgePicList.size()>0){
                            RequestQueue mRequestQueue = app.getRequestQueue();
                            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.APPLY, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                    if (JsonParseUtil.isSuccess(mJsonObj)){
                                        if ("1".equals(mJsonObj.get("response_data").getAsString())){
                                            Toast.makeText(SubmitDataActivity.this,"已提交申请,请耐心等待",Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(SubmitDataActivity.this,"提交失败",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(SubmitDataActivity.this,"提交失败",Toast.LENGTH_LONG).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("benId",shoppingId);
                                    params.put("userId", UsersUtil.getUserId(SubmitDataActivity.this));
                                    if (iamgePicList.size()>0){
                                        for (int i = 0;i<iamgePicList.size();i++){
                                            params.put("baImg"+(i+1),iamgePicList.get(i));
                                        }
                                    }
                                    params.put("addId","010101010101");
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
