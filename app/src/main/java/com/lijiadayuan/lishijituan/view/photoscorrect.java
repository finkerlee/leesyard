package com.lijiadayuan.lishijituan.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.MemberActivity;
import com.lijiadayuan.lishijituan.R;

/**
 * Created by lifanqiao on 16/4/21.
 */
public class photoscorrect extends Dialog {
    private TextView tvGallery, tvPhoto, tvCancel;
    private View.OnClickListener mListener;

    public photoscorrect(Context context, int theme) {
        super(context,theme);
        Window window = this.getWindow();
        mListener = (View.OnClickListener) context;
        window.setGravity(Gravity.BOTTOM);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        tvGallery = (TextView) findViewById(R.id.tv_gallery);
        tvPhoto = (TextView) findViewById(R.id.tv_photo);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvGallery.setOnClickListener(mListener);
        tvPhoto.setOnClickListener(mListener);
        tvCancel.setOnClickListener(mListener);
    }
}
