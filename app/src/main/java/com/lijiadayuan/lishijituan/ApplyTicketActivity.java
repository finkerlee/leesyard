package com.lijiadayuan.lishijituan;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.UpLoadPicAdapter;
import com.lijiadayuan.lishijituan.bean.Reds;
import com.lijiadayuan.lishijituan.bean.Tickets;
import com.lijiadayuan.lishijituan.http.UrlConstants;
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

/**
 *
 * 申请领取红包页面
 */

public class ApplyTicketActivity extends BaseActivity implements OnClickListener {
    //视图bean
    private Tickets mTickets;

    private final int OPEN_ALBUM_FLAG = 1023;
    private final int OPEN_CAMERA_FLAG = 1024;
    private TextView tvTitle;
    private ImageView imageback;
    //软键盘管理器
    private InputMethodManager manager;
    private ReceiveDialog dialog;
    private static ApplyTicketActivity instance;
    private GridView mGridView;
    private EditText mEtName,mEtPhoneNum,mEtAccount;

    private String mSaveDir;//拍照存放的文件夹名字
    private String mFileName;//拍照存放的文件的名字

    private UpLoadPicAdapter mAdpter;
    private ArrayList<Bitmap> mBitmaps;
    private Bitmap[] bitmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reds_apply);
        instance = this;
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mTickets = getIntent().getParcelableExtra(KeyConstants.IntentPageValues.Tickets);
        if ("".equals(mTickets.getTktId())){
            Toast.makeText(ApplyTicketActivity.this,"获取卡票信息失败",Toast.LENGTH_LONG).show();
            return;
        }
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
                    dialog = new ReceiveDialog(ApplyTicketActivity.this, R.style.protocol_dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                } else {

                }
            }
        });

//        mEtNation.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickerView.setTitle("选择民族");
//                pickerView.setPicker(ArchivesUtils.getNationList());
//                pickerView.setCyclic(false);
//                if(!TextUtils.isEmpty(strUserAge)){
//                    pickerView.setSelectOptions(Integer.valueOf(strUserAge));
//                }
//                pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3) {
//                        mEtNation.setText(ArchivesUtils.getNationList().get(options1));
//                        nationId = (options1 +1) +"";
//
//                    }
//                });
//                if(!pickerView.isShowing()){
//                    pickerView.show();
//                }
//            }
//        });

        mEtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
        mEtPhoneNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
    }

    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        mGridView = (GridView) findViewById(R.id.up_load_pic);
        mEtName = (EditText) findViewById(R.id.name);
        mEtPhoneNum = (EditText) findViewById(R.id.phone_num);
        mEtAccount = (EditText) findViewById(R.id.account_name);
        findViewById(R.id.btn_receive).setOnClickListener(this);
        tvTitle.setText("卡票领取");
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

    public static ApplyTicketActivity getInstance() {
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
                    mAdpter.UpDataView(mBitmaps);
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
                    Toast.makeText(ApplyTicketActivity.this,"请填写申请人姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtAccount.getText().toString().isEmpty()){

                    Toast.makeText(ApplyTicketActivity.this,"请添加开户行名称",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mEtPhoneNum.getText().toString().isEmpty()){
                    Toast.makeText(ApplyTicketActivity.this,"请填写手机号",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mBitmaps.size() == 1){
                    Toast.makeText(ApplyTicketActivity.this,"请上传图片",Toast.LENGTH_LONG).show();
                    return;
                }
                bitmaps = new Bitmap[mBitmaps.size()-1];
                for (int i = 0; i<mBitmaps.size()-1; i++){
                    bitmaps[i] = mBitmaps.get(i);
                }
                UpLoadImageTask1 mUpLoadImageTask1 = (UpLoadImageTask1) new UpLoadImageTask1(ApplyTicketActivity.this,bitmaps).execute(UrlConstants.UPLOAD_TICAPPLY);
                mUpLoadImageTask1.setCallBACK(new UpLoadPicCallBack() {
                    @Override
                    public void setCompleteImage(final ArrayList<String> iamgePicList) {
                        if (iamgePicList.size()>0){
                            RequestQueue mRequestQueue = app.getRequestQueue();
                            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.TICAPPLY_CERTIFICATION, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                                    if (JsonParseUtil.isSuccess(mJsonObj)){
                                        String response_data =  mJsonObj.get("response_data").getAsString();
                                        if ("1".equals(response_data)){
                                            Toast.makeText(ApplyTicketActivity.this,"提交成功，请耐心等待",Toast.LENGTH_LONG).show();
                                            finish();
                                        }else{
                                            Toast.makeText(ApplyTicketActivity.this,response_data,Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ApplyTicketActivity.this,"faile",Toast.LENGTH_LONG).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("tktId", mTickets.getTktId());//红包id
                                    params.put("userId",UsersUtil.getUserId(ApplyTicketActivity.this));//用户id
                                    params.put("addId",mEtAccount.getText().toString());//开户行名称
                                    params.put("name",mEtName.getText().toString());
                                    params.put("phone",mEtPhoneNum.getText().toString());
                                    if (iamgePicList.size()>0){
                                        for (int i = 0;i<iamgePicList.size();i++){
                                            params.put("taImg"+(i+1),iamgePicList.get(i));
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
