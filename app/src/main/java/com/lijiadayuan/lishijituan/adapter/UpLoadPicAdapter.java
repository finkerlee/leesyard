package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/5/5.
 */
public class UpLoadPicAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Bitmap> mData;

    public UpLoadPicAdapter(Context mContext,ArrayList<Bitmap> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        if (mData.size() > 4){
            return 4;
        }else{
            return mData.size();
        }
    }

    public void UpDataView(ArrayList<Bitmap> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int i) {
        if (mData == null){
            return 0;
        }
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler holder = null;
        if (view == null){
            holder = new ViewHodler();
            view = View.inflate(mContext,R.layout.item_up_load_pic,null);
            holder.mImageView = (ImageView) view.findViewById(R.id.iv_photos);
            view.setTag(holder);
        }else{
            holder = (ViewHodler) view.getTag();
        }
        holder.mImageView.setImageBitmap(mData.get(i));
        return view;
    }
    class ViewHodler{
        ImageView mImageView;
    }
}
