package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

public abstract class BaseActivity extends Activity {

	protected LeeApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (LeeApplication) getApplication();
	}

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public int getWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

//	//两次退出
//	private static Boolean isQuit = false;
//	Timer timer = new Timer();
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (isQuit == false) {
//				isQuit = true;
//				Toast.makeText(getBaseContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
//				TimerTask task = null;
//				task = new TimerTask() {
//					@Override
//					public void run() {
//						isQuit = false;
//					}
//				};
//				timer.schedule(task, 2000);
//			} else {
//				finish();
//				System.exit(0);
//			}
//		}
//		return true;
//	}


}
