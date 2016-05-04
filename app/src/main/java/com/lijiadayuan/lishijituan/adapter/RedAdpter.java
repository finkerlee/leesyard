package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;

/**
 * Created by Administrator on 2016/5/3.
 */
public class RedAdpter extends BaseAdapter {

        private Context mContext;
        private String[] mlist;
        private int mItemId;

        public RedAdpter(Context mContext, String[] mlist, int mItemId) {
            this.mContext = mContext;
            this.mlist = mlist;
            this.mItemId = mItemId;
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
            final  ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mContext,mItemId,null);
                viewHolder = new ViewHolder();
                viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.itemImage);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.image.setImageURI(Uri.parse(mlist[position]));
            return convertView;
        }


    class ViewHolder {
        public SimpleDraweeView image;

    }

}
