package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.view.RoundRectImageView;
import com.lijiadayuan.lishijituan.view.XCRoundRectImageView;
import com.lijiadayuan.lishijituan.bean.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifanqiao on 16/3/31.
 */
public class PictureAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Picture> pictures;
    private Context context;
    private int layoutId;

    public PictureAdapter(int[] images, Context context, int layoutId) {
        super();
        pictures = new ArrayList<Picture>();
        this.context = context;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < images.length; i++) {
            Picture picture = new Picture(images[i]);
            pictures.add(picture);
        }
    }

    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (RoundRectImageView) convertView.findViewById(R.id.itemImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image.setImageResource(pictures.get(position).getImageId());
        return convertView;
    }


    class ViewHolder {
        public RoundRectImageView image;

    }
}

