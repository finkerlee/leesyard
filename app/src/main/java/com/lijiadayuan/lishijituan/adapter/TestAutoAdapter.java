package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.bean.Benefits;

import java.util.List;

/**
 * Created by lifanqiao on 16/5/7.
 */
public class TestAutoAdapter extends BaseAdapter {

    private int resId;
    private List<Benefits> benefitList;
    private Context context;


    public TestAutoAdapter(Context context, int resId, List<Benefits> benefitList){
        this.context = context;
        this.resId = resId;
        this.benefitList = benefitList;
    }


    @Override
    public int getCount() {
        return benefitList.size();
    }

    @Override
    public Object getItem(int position) {
        return benefitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resId, parent, false);
            viewHolder.simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.itemImage);
            viewHolder.simpleDraweeView.setImageURI(Uri.parse(benefitList.get(position).getBenThumb()));
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    public class ViewHolder{
        public SimpleDraweeView simpleDraweeView;
    }
}
