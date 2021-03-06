package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;

/**
 * Created by zhaoyi on 16/4/27.
 */
public class PictureAdpter2 extends BaseAdapter{

    private Context mContext;
    private int[] mlist;
    private int mItemId;

    public PictureAdpter2(Context mContext, int[] mlist, int i) {
        this.mContext = mContext;
        this.mlist = mlist;
        this.mItemId = i;
    }

    @Override
    public int getCount() {
        return mlist.length;
    }

    @Override
    public Object getItem(int i) {
        if (mlist == null){
            return 0;
        }
        return mlist[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        if (view ==null){
            mHolder = new ViewHolder();
            view = View.inflate(mContext,mItemId,null);
            mHolder.mImageView = (ImageView) view.findViewById(R.id.itemImage);
            view.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) view.getTag();
        }
        mHolder.mImageView.setBackgroundResource(mlist[i]);
        return view;
    }

    class ViewHolder{
        ImageView mImageView;
    }
}
