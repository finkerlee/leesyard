package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.DialogUtil;
import com.lijiadayuan.lishijituan.utils.IDCard;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UpLoadImageTask;
import com.lijiadayuan.lishijituan.utils.UpLoadImageTask1;
import com.lijiadayuan.lishijituan.utils.UpLoadPicCallBack;
import com.lijiadayuan.lishijituan.utils.VerficationUtil;
import com.lijiadayuan.lishijituan.view.photoscorrect;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.lijiadayuan.lishijituan.R.id.iv_photos_opposite;

public class MemberActivity extends BaseActivity implements OnClickListener {

        private TextView tvTitle;
        private EditText name;//同户名
        private EditText phoneNum;//手机号
        private EditText id;//身份证
        private Button mBtnVerification;


        private final int OPEN_ALBUM_FLAG = 1023;//第一张相册打开
        private final int OPEN_CAMERA_FLAG = 1024;//第一张拍照
        private final int TAKING_ALBUM_FLAG = 1025;//第二张相册打开
        private final int TAKING_CAMERA_FLAG = 1026;//第二张拍照
        private SimpleDraweeView photos, photos2;
        InputMethodManager manager;
        private photoscorrect dialog;
        private static MemberActivity instance;
        private SimpleDraweeView mShowIV, mshowIV2;
        private String mSaveDir;//拍照存放的文件夹名字
        private String mFileName;//拍照存放的文件的名字
        private String sex = "1";

        private int photoFlag = 0;

        private String goodsPageValue = KeyConstants.IntentPageValues.normol;
        private SharedPreferences mSharedPreferences;
        //存放身份证正反面的集合
        //private ArrayList<Bitmap> mBitmapList;
        private Bitmap[] mBitmapList = new Bitmap[2];

        private String[] mlist = new String[2];

        private DialogUtil mDialogUtil;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member);
            //mBitmapList = new ArrayList<>();
            mDialogUtil = new DialogUtil(MemberActivity.this);
            mSharedPreferences = getSharedPreferences("userInfo",Activity.MODE_PRIVATE);
            if (getIntent() != null) {
                goodsPageValue = getIntent().getStringExtra(KeyConstants.IntentPageKey.GoodsPageType);
            }

            //空白处隐藏软键盘
            instance = this;
            manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            findViewById();
            initView();
        }

        protected void findViewById() {
            tvTitle = (TextView) findViewById(R.id.text_title);

            findViewById(R.id.iv_back).setOnClickListener(this);
            photos = (SimpleDraweeView) findViewById(R.id.iv_photos_correct);
            mShowIV = (SimpleDraweeView) findViewById(R.id.iv_photos_correct);
            photos2 = (SimpleDraweeView) findViewById(R.id.iv_photos_opposite);
            mshowIV2 = (SimpleDraweeView) findViewById(R.id.iv_photos_opposite);
            RadioButton rbMan = (RadioButton) findViewById(R.id.rb_man);
            RadioButton rbWoMan = (RadioButton) findViewById(R.id.rb_woman);
            name = (EditText) findViewById(R.id.et_name);//名字
            phoneNum = (EditText) findViewById(R.id.et_phoneNum);//手机号
            id = (EditText) findViewById(R.id.et_id);//身份证
            mBtnVerification = (Button) findViewById(R.id.btn_verification);
            mBtnVerification.setOnClickListener(this);
            rbMan.setChecked(true);
            rbMan.setOnClickListener(this);
            rbWoMan.setOnClickListener(this);
        }

        protected void initView() {
            tvTitle.setText("认证会员");
            photos.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    photoFlag = 1;
                    dialog = new photoscorrect(MemberActivity.this, R.style.protocol_dialog);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }
            });
            photos2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    photoFlag = 2;
                    dialog = new photoscorrect(MemberActivity.this, R.style.protocol_dialog2);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }
            });
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

        public static MemberActivity getInstance() {
            return instance;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            File file;
            OutputStream outputStream;
            Bitmap bitmap;

            if (resultCode == RESULT_OK) {
                switch (requestCode) {

                    case OPEN_ALBUM_FLAG://从相册获取回调
                        if (1 == photoFlag) {
                            // 给第一个赋值
                            Uri originUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor cursor = managedQuery(originUri, proj, null, null, null);
                            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(columnIndex);
                            bitmap = getCompressBitmap(path);
                            mBitmapList[0] = bitmap;
                            mShowIV.setImageURI(originUri);
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

                        } else if (2 == photoFlag) {
                            Uri originUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor cursor = managedQuery(originUri, proj, null, null, null);
                            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(columnIndex);
                            bitmap = getCompressBitmap(path);
                            //mBitmapList.set(1,bitmap);
                            mBitmapList[1] = bitmap;
                            mshowIV2.setImageURI(originUri);
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
                        }

                        break;
                    case OPEN_CAMERA_FLAG://拍照获取的回调
                        if (1 == photoFlag) {
                            file = new File(mSaveDir + mFileName);//拍照前指定的输出路径
                            bitmap = getCompressBitmap(mSaveDir + mFileName);
                            //mBitmapList.set(0,bitmap);
                            mBitmapList[0] = bitmap;
                            mShowIV.setImageBitmap(bitmap);
                            try {
                                outputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                System.out.println("file size:" + file.length());
                        /* 到这里已经成功将bitmap写入file了，此时可以将file或者流发送给服务器了 */
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else if (2 == photoFlag) {
                            file = new File(mSaveDir + mFileName);//拍照前指定的输出路径
                            bitmap = getCompressBitmap(mSaveDir + mFileName);
                            //mBitmapList.set(0,bitmap);
                            mBitmapList[1] = bitmap;
                            mshowIV2.setImageBitmap(bitmap);
                            try {
                                outputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                System.out.println("file size:" + file.length());
                        /* 到这里已经成功将bitmap写入file了，此时可以将file或者流发送给服务器了 */
                                break;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
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
                    startActivityForResult(intent, OPEN_ALBUM_FLAG);//
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
                case R.id.rb_man:
                    sex = "1";
                    break;
                case R.id.rb_woman:
                    sex = "0";
                    break;

                case R.id.btn_verification:
                    //TODO  访问服务器 认证成功后返回   更新本地的数据
                    /**
                     * 1,先访问服务器上传照片
                     * 2,根据返回的地址去提交认证申请
                     */
                    mDialogUtil.show();
                    UpLoadImageTask1  mUpLoadImageTask1 = (UpLoadImageTask1) new UpLoadImageTask1(MemberActivity.this,mBitmapList).execute(UrlConstants.UPLOAD_IDENTIFY);
                    mUpLoadImageTask1.setCallBACK(new UpLoadPicCallBack() {
                        @Override
                        public void setCompleteImage(ArrayList<String> iamgePicList) {
                            if (iamgePicList.size() == 2){
                                mlist[0] = iamgePicList.get(0);
                                mlist[1] = iamgePicList.get(1);
                                //认证会员
                                Verification();
                            }else{
                                Toast.makeText(MemberActivity.this,"认证失败,请重新提交",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    Intent inten = new Intent(this, ProductBaseActivity.class);
                    if (goodsPageValue == KeyConstants.IntentPageValues.forResult) {
                        inten.putExtra(KeyConstants.UserInfoKey.userIfLee, true);
                        setResult(RESULT_OK, inten);
                        finish();
                    }
                    break;
                default:

                    break;
            }
        }


        /**
         * 检查参数，请求服务，认证会员
         */
        private void Verification(){
            if (TextUtils.isEmpty(name.getText()) || name.getText() == null) {
                Toast.makeText(MemberActivity.this, "请输入名字", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(phoneNum.getText()) || phoneNum.getText() == null) {
                Toast.makeText(MemberActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                return;
            }
            if (!VerficationUtil.checkMobile(MemberActivity.this,phoneNum.getText().toString())){
                return;
            }
            if (TextUtils.isEmpty(id.getText()) || id.getText() == null) {
                Toast.makeText(MemberActivity.this, "请输入身份证号", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                String msg = IDCard.IDCardValidate(id.getText().toString());
                if (!"".equals(msg)){
                    Toast.makeText(MemberActivity.this, msg, Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (mBitmapList.length != 2) {
                Toast.makeText(MemberActivity.this, "上传照片失败,请重试", Toast.LENGTH_LONG).show();
                return;
            }


            // 创建请求队列
            RequestQueue mQueue = app.getRequestQueue();
            StringRequest mStringRequest = new StringRequest(Request.Method.POST, UrlConstants.CERTIFICATION, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                    if(JsonParseUtil.isSuccess(mJsonObject)) {
                        mDialogUtil.dismiss();
                        if (1 == mJsonObject.get("response_data").getAsInt()){
                            Toast.makeText(MemberActivity.this,"资料已提交，请耐心等待",Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            Toast.makeText(MemberActivity.this,"资料提交失败",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDialogUtil.dismiss();
                    Toast.makeText(MemberActivity.this,"资料提交失败",Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userId", mSharedPreferences.getString(KeyConstants.UserInfoKey.userId, ""));
                    params.put("verName", name.getText().toString());
                    params.put("verIdentify", id.getText().toString());
                    params.put("verGender", sex);
                    params.put("verImg1",mlist[0]);
                    params.put("verImg2",mlist[1]);
                    return params;
                }
            };

            mQueue.add(mStringRequest);
        }

}