package com.lijiadayuan.lishijituan.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/22.
 * 图片上传工具类
 */
public class UpLoadImageTask1 extends AsyncTask<String, Integer, String[]> {
    private Context mContext;
    private Bitmap[] mList;

    private ProgressDialog dialog;

    private UpLoadPicCallBack mCallback;

    private String[] mPic = new String[2];

    public UpLoadImageTask1(Context mContext, Bitmap[] mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    protected String[] doInBackground(String... strings) {
        for (int i = 0; i < mList.length; i++) {
            mPic[i] = uploadFile(strings[0], "iden"+i, mList[i]);
        }
        return mPic;
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        //dialog.dismiss();
        if (result.length != 2) {
            Toast.makeText(mContext, "上传照片失败", Toast.LENGTH_LONG).show();
        }
        ArrayList<String> mList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            if (!TextUtils.isEmpty(result[i])) {
                try {
                    JSONObject jsonOjbect = new JSONObject(result[i]);
                    String ret = jsonOjbect.getString("response_data");
                    Log.i("UpLoadImageTask", ret);
                    //ret = ret.substring(ret.lastIndexOf("/"), ret.length());
                    if (!"".equals(ret)) {
                        mList.add(ret);
                    }
                } catch (JSONException e) {
                    Log.e("UpLoadImageTask", e.getMessage());
                }
            }
        }
        mCallback.setCompleteImage(mList);


    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
//        dialog = new ProgressDialog(mContext);
//        dialog.setTitle("提示");
//        dialog.setMessage("正在上传图片...");
//        dialog.show();
    }

    //    /**
//     * 上传文件至Server的方法
//     *
//     * @param uploadUrl
//     * @param newName
//     * @param uploadFile
//     * @return
//     */
    private static String uploadFile(String uploadUrl, String newName, Bitmap bitmap) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设置传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());


            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
            /* 取得bitmap的InputStream */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            InputStream fStream = new ByteArrayInputStream(baos.toByteArray());
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            fStream.close();
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            ds.flush();
            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            /* 将Response显示于Dialog */
            // showDialog("上传成功" + b.toString().trim());
            /* 关闭DataOutputStream */
            ds.close();
            return b.toString();
        } catch (Exception e) {
            // showDialog("上传失败" + e);
            Log.e("UpLoadImage", e.getMessage());
        }
        return null;
    }

    public void setCallBACK(UpLoadPicCallBack mCallback) {
        this.mCallback = mCallback;
    }


}
