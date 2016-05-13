/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.lijiadayuan.lishijituan.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.BaseWebActivity;
import com.lijiadayuan.lishijituan.MainActivity;
import com.lijiadayuan.lishijituan.ProductBaseActivity;
import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.bean.AdvView;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @Description: 图片适配器
 * @author http://blog.csdn.net/finddreams
 */ 
public class ImagePagerAdapter extends BaseAdapter {

	private Context context;
	private List<AdvView> mAdvViewList;
	private int size;
	private boolean isInfiniteLoop;
	//private DisplayImageOptions options;

	public ImagePagerAdapter(Context context, List<AdvView> mAdvViewList) {
		this.context = context;
		this.mAdvViewList = mAdvViewList;
		isInfiniteLoop = false;

	}

	public ImagePagerAdapter(MainActivity mainActivity, ArrayList<String> imageUrlList, ArrayList<String> linkUrlArray, ArrayList<String> titleList) {
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : mAdvViewList.size();
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
			view = holder.imageView = new SimpleDraweeView(context);
			holder.imageView
					.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.imageView.setImageURI(Uri.parse(mAdvViewList.get(position).getAdvImg()));
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AdvView mAdvView = mAdvViewList.get(position);

				ProductViewBean mProductViewBean = new ProductViewBean();
				mProductViewBean.setGoodsPrice(mAdvView.getProPrice()+"");
				mProductViewBean.setGoodsName(mAdvView.getProName());
				mProductViewBean.setGoodsInfoUrl(UrlConstants.FINDSHOPPING_INFO + mAdvView.getProId());
				mProductViewBean.setGoodsSpec(mAdvView.getProSpec());
				mProductViewBean.setGoodsNum(mAdvView.getProStock()+"");
				mProductViewBean.setGoodsThumb(mAdvView.getProThumb());
				mProductViewBean.setGoodsPic(mAdvView.getProImg());
				mProductViewBean.setGoodsId(mAdvView.getProId());
				mProductViewBean.setGoodsOtherName(mAdvView.getProSubtitle());
				String [] pics = mAdvView.getProImg().split(",");
				ArrayList<String> mlist = new ArrayList<>();
				for (String s : pics){
					mlist.add(s);
				}
				mProductViewBean.setGoodsType(ProductBaseActivity.BUY_GOODS);
				mProductViewBean.setPicList(mlist);

				Intent mIntent = new Intent(context,ProductBaseActivity.class);
				mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType,mProductViewBean);
				context.startActivity(mIntent);
			}
		});

		return view;
	}

	private static class ViewHolder {

		SimpleDraweeView imageView;
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
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
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
