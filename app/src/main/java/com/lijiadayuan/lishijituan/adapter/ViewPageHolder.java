package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zhaoyi on 16/5/21.
 */
public class ViewPageHolder implements Holder<String>{
    private SimpleDraweeView mSimpleDraweeView;

    @Override
    public View createView(Context context) {
        mSimpleDraweeView = new SimpleDraweeView(context);
        return mSimpleDraweeView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        mSimpleDraweeView.setImageURI(Uri.parse(data));
    }
}
