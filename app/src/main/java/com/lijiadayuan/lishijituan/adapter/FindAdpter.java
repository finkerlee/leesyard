package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.bean.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */
public class FindAdpter extends RecyclerView.Adapter<FindAdpter.FindViewholder>{

    private Context mContext;
    private List<Product> mList;

    public FindAdpter(Context mContext,List<Product> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public FindViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item_find,null);
        return new FindViewholder(view);
    }

    @Override
    public void onBindViewHolder(FindViewholder holder, int position) {
        Product mProduct = mList.get(position);
        holder.mSvGoodsPic.setImageURI(Uri.parse(mProduct.getProThumb()));
        holder.mTvGoodsName.setText(mProduct.getProName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class FindViewholder extends RecyclerView.ViewHolder{
        SimpleDraweeView mSvGoodsPic;
        TextView mTvGoodsName;
        public FindViewholder(View itemView) {
            super(itemView);
            mSvGoodsPic = (SimpleDraweeView) itemView.findViewById(R.id.item_goodsImage);
            mTvGoodsName = (TextView) itemView.findViewById(R.id.item_goodsName);
        }
    }
}

