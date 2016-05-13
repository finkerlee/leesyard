package com.lijiadayuan.lishijituan.utils;

import android.content.Context;

import com.mingle.widget.ShapeLoadingDialog;

/**
 * Created by zhaoyi on 16/5/13.
 */
public class DialogUtil {
    ShapeLoadingDialog shapeLoadingDialog;
    public DialogUtil(Context mContext) {
        shapeLoadingDialog = new ShapeLoadingDialog(mContext);
        shapeLoadingDialog.setLoadingText("加载中...");
    }

    public void show(){
        shapeLoadingDialog.show();
    }

    public void dismiss(){
        shapeLoadingDialog.dismiss();
    }
}
