package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.bean.OrderBean;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/21.
 */
public class OrderAdpter extends BaseAdapter {
    private ArrayList<OrderBean> mOrderList;
    private Context mContext;

    public OrderAdpter(ArrayList<OrderBean> mOrderList, Context mContext) {
        this.mOrderList = mOrderList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mOrderList.size();
    }

    @Override
    public Object getItem(int i) {
        return mOrderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == view){
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_order,null);
            holder.mIvPic = (SimpleDraweeView) view.findViewById(R.id.itemImage);
            holder.mIvDelete = (ImageView) view.findViewById(R.id.delete_goods);
            holder.mTvGoodsName = (TextView) view.findViewById(R.id.goods_name);
            holder.mTvGoodsPrice = (TextView) view.findViewById(R.id.goods_price);
            holder.mTvUnitprice = (TextView) view.findViewById(R.id.tv_unitprice);
            holder.mTvNum = (TextView) view.findViewById(R.id.tv_num);
            holder.mTvStatus = (TextView) view.findViewById(R.id.goods_status);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        OrderBean mOrderBean = mOrderList.get(i);
        holder.mTvGoodsName.setText(mOrderBean.getShoppingName());
        holder.mTvGoodsPrice.setText(mOrderBean.getShoppingPrice());
        holder.mTvUnitprice.setText(""+mOrderBean.getShoppingUnitPrice());
        holder.mTvNum.setText(mOrderBean.getShoppingNum());
        holder.mTvStatus.setText(mOrderBean.getOrderStatus());

        return view;
    }
    class ViewHolder{
        SimpleDraweeView mIvPic;
        TextView mTvGoodsName;
        TextView mTvGoodsPrice;
        TextView mTvUnitprice;
        TextView mTvNum;
        TextView mTvStatus;
        ImageView mIvDelete;
    }
}
