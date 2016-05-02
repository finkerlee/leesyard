package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;

/**
 * Created by Administrator on 2016/4/30.
 */
public class FindAdpter extends BaseAdapter{

    private Context mContext;
    private String[] mlist;
    private int mItemId;

    public FindAdpter(Context mContext, String[] mlist,int mItemId ) {
        this.mContext = mContext;
        this.mItemId = mItemId;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView ==null){
            mHolder = new ViewHolder();
            convertView = View.inflate(mContext,mItemId,null);
            mHolder.findImageView = (SimpleDraweeView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.findImageView.setImageURI(Uri.parse(mlist[position])); //接口有数据后 用此方法获取

        return convertView;
    }

    class ViewHolder{
        SimpleDraweeView findImageView;
    }
}

