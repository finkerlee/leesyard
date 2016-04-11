package com.lijiadayuan.lishijituan.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lijiadayuan.lishijituan.R;

/**
 * Created by lifanqiao on 16/3/9.
 */
public class ProtocolDialog extends Dialog {




    public ProtocolDialog(Context mContext,int resId) {
        super(mContext, resId);
//        setCancelable(false);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.selected);
//        this.clickListener = clickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xyuers);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

    }
}
