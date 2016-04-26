package com.lijiadayuan.lishijituan.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.RegistrationActivity;

/**
 * Created by lifanqiao on 16/4/5.
 */
public class PhotosDialog extends Dialog {

    private TextView tvGallery, tvPhoto, tvCancel;


    public PhotosDialog(Context context,int theme) {
        super(context,theme);
        Window window = this.getWindow();
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
        tvGallery.setOnClickListener(RegistrationActivity.getInstance());
        tvPhoto.setOnClickListener(RegistrationActivity.getInstance());
        tvCancel.setOnClickListener(RegistrationActivity.getInstance());
    }
}
