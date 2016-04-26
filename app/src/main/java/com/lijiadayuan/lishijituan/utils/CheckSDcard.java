package com.lijiadayuan.lishijituan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhaoyi on 16/4/20.
 */
public class CheckSDcard {
    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public static String saveBitmap(Bitmap mBitmap,Context mContext) {
        String newFilePath = "";
        if (hasSdcard()){
            File file = new File("/sdcard/lishijituan");
            if (!file.exists())
                file.mkdir();
            file = new File("/sdcard/temp.jpg".trim());
            String fileName = file.getName();
            String mName = fileName.substring(0, fileName.lastIndexOf("."));
            String sName = fileName.substring(fileName.lastIndexOf("."));
            newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped" + sName;
            file = new File(newFilePath);

            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            newFilePath = mContext.getFilesDir() + "headImage.jpg";
            File file = new File(newFilePath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return newFilePath;

    }

}
