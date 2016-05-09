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
import android.widget.ImageView;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.view.ReceiveDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ReceiveActivity extends Activity implements OnClickListener {
    private final int OPEN_ALBUM_FLAG = 1023;
    private final int OPEN_CAMERA_FLAG = 1024;
    private TextView tvTitle;
    private ImageView photos;
    InputMethodManager manager;
    private ReceiveDialog dialog;
    private static ReceiveActivity instance;
    private ImageView mShowIV;
    private String mSaveDir;//拍照存放的文件夹名字
    private String mFileName;//拍照存放的文件的名字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        instance = this;
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        findViewById();
        initView();
    }
    protected void findViewById() {
        tvTitle = (TextView) findViewById(R.id.text_title);


        findViewById(R.id.iv_back).setOnClickListener(this);
        photos= (ImageView) findViewById(R.id.iv_photos);
        mShowIV = (ImageView) findViewById(R.id.iv_photos);
    }
    protected void initView() {
        photos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog = new ReceiveDialog(ReceiveActivity.this, R.style.protocol_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        tvTitle.setText("申请报名");
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
    public static ReceiveActivity getInstance() {
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
                    mShowIV.setImageBitmap(bitmap);//将选中的图片展示出来
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
            default:
                break;
        }
    }
}
