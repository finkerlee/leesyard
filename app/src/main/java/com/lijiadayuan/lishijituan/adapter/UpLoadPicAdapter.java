package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodler holder = null;
        if (view == null){
            holder = new ViewHodler();
            view = View.inflate(mContext,R.layout.item_up_load_pic,null);
            holder.mImageView = (SimpleDraweeView) view.findViewById(R.id.iv_photos);
            holder.mTvDelete = (TextView) view.findViewById(R.id.delete);
            view.setTag(holder);
        }else{
            holder = (ViewHodler) view.getTag();
        }
        holder.mImageView.setImageBitmap(mData.get(i));
        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(i);
                UpDataView(mData);
            }
        });
        return view;
    }
    class ViewHodler{
        SimpleDraweeView mImageView;
        TextView mTvDelete;
    }
}
