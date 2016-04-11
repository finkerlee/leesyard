package com.lijiadayuan.lishijituan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.BaseWebActivity;
import com.lijiadayuan.lishijituan.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by lifanqiao on 16/4/8.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private int size;
    private boolean isInfiniteLoop;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ImageAdapter(Context context, List<String> imageIdList,
                             List<String> urllist) {
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报错
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.meinv) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.meinv) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView
                    .setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        imageLoader.displayImage(
                (String) this.imageIdList.get(getPosition(position)),
                holder.imageView, options);

        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = linkUrlArray.get(ImageAdapter.this
                        .getPosition(position));
				/*
				 * if (TextUtils.isEmpty(url)) {
				 * holder.imageView.setEnabled(false); return; }
				 */
                Bundle bundle = new Bundle();

                bundle.putString("url", url);
                Intent intent = new Intent(context, BaseWebActivity.class);
                intent.putExtras(bundle);

                context.startActivity(intent);
                Toast.makeText(context, "点击了第" + getPosition(position) + "美女", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop
     *            the isInfiniteLoop to set
     */
    public ImageAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }
}
