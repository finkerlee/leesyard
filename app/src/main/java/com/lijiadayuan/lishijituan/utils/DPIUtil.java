package com.lijiadayuan.lishijituan.utils;

import android.content.Context;
import android.view.Display;
public class DPIUtil {
    //当前屏幕的像素密度
	private static float mDensity = 0;
	//当前屏幕的长度
	private static int width = 0;
	//当前屏幕的宽度
	private static int height = 0;

	private static Display defaultDisplay;

	public static float getmDensity() {
		return mDensity;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static Display getDefaultDisplay() {
		return defaultDisplay;
	}

	public static void setmDensity(float mDensity) {
		DPIUtil.mDensity = mDensity;
	}

	public static void setWidth(int width) {
		DPIUtil.width = width;
	}

	public static void setHeight(int height) {
		DPIUtil.height = height;
	}

	public static void setDefaultDisplay(Display defaultDisplay) {
		DPIUtil.defaultDisplay = defaultDisplay;
	}

	public static int dip2px(float dipValue) {
		return (int) (dipValue * mDensity + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		return (int) (pxValue / mDensity + 0.5f);
	}
}
