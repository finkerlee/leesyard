package com.lijiadayuan.lishijituan.utils;

import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.bean.NewMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyi on 16/5/5.
 */
public class LocalUtils<T extends Serializable & Parcelable> {
    private String mFilePath = "";

    public LocalUtils(String mFile) {
        this.mFilePath = mFile;
    }


//    /**
//     * 向本地写入数据
//     * */
//    public void put(List<T> list) {
//
//        try {
//
//
//            if (mFile.exists()){
//                mFile.mkdirs();
//            }
//            // 打开文件
//            FileOutputStream fos = new FileOutputStream(mFile);
//
//            // 将数据写入文件
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(list);
//
//            // 释放资源
//            oos.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    /**
     * 向本地写入数据
     * */
    public void put(List<T> mT) {

        try {
            // 打开文件
            FileOutputStream fos = new FileOutputStream(mFilePath);


            // 将数据写入文件
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.reset();
            oos.writeObject(mT);

            // 释放资源
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地读取数据
     * */
    @SuppressWarnings("unchecked")
    private List<T> get() {
        List<T> list = new ArrayList<T>();
        try {

            if ("".equals(mFilePath)){
                return list;
            }

            // 打开文件
            FileInputStream fis = new FileInputStream(mFilePath);

            // 读取文件
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();

            // 释放资源
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 删除本地数据
     */
    public void deleteFile(){
        File mFile = new File(mFilePath);
        if (mFile.exists()){
            mFile.delete();
        }
    }

    /**
     * 读取缓存数据
     *
     * @return 缓存数据，数据为空时返回长度为0的list
     * */
    public synchronized List<T> read() {
        return get();
    }
}
