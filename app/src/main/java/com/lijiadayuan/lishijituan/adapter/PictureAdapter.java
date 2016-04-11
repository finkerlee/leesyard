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

//        if (R.layout.large_image == layoutId) {
////            Drawable drawable = null;
////            try {
////                drawable = context.getResources().getDrawable(pictures.get(position).getImageId());
////            } catch (Exception e){
////                // op.inJustDecodeBounds = true;表示我们只读取Bitmap的宽高等信息，不读取像素。
////                BitmapFactory.Options op = new BitmapFactory.Options();
////                // op.inSamplySize 表示的是缩小的比例
////                // op.inSamplySize = 4,表示缩小1/4的宽和高，1/16的像素，android认为设置为2是最快的。
////                op.inSampleSize = 2;
////                op.inJustDecodeBounds = true;
////                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), pictures.get(position).getImageId(), op);
////                viewHolder.image.setImageBitmap(bitmap);
////                return convertView;
////            }
////
////            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
////            int width = dm.widthPixels;
////            int height = width * 28 / 75;
////            System.out.println("width: "+width);
////            System.out.println("height: "+height);
////            drawable.setBounds(0, 0, width, height);
////            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//            BitmapFactory.Options op = new BitmapFactory.Options();
//            op.inJustDecodeBounds = true;
//            Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), pictures.get(position).getImageId(), op);
//            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
//            int width = dm.widthPixels;
//            int height = width * 28 / 75;
//            int wRatio = (int) Math.ceil(op.outWidth / (float) width);
//            int hRatio = (int) Math.ceil(op.outHeight / (float) height);
//            if (1 > wRatio || 1 > hRatio){
//                if (wRatio > hRatio)
//                    op.inSampleSize = wRatio;
//                else
//                    op.inSampleSize = hRatio;
//            }
//            op.inJustDecodeBounds = false;
//            tempBitmap = BitmapFactory.decodeResource(context.getResources(), pictures.get(position).getImageId(), op);
//            viewHolder.image.setImageBitmap(Bitmap.createScaledBitmap(tempBitmap, width, height, true));
//        } else{
            viewHolder.image.setImageResource(pictures.get(position).getImageId());
//        }
        return convertView;
    }


    class ViewHolder {
        public RoundRectImageView image;

    }
}

