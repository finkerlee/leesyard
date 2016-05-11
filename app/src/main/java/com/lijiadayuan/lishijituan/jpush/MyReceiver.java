package com.lijiadayuan.lishijituan.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lijiadayuan.lishijituan.LeeApplication;
import com.lijiadayuan.lishijituan.MainActivity;
import com.lijiadayuan.lishijituan.bean.MyMessage;
import com.lijiadayuan.lishijituan.bean.NewMessage;
import com.lijiadayuan.lishijituan.utils.LocalUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		Log.d(TAG,"我的消息呢？");
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            
        	//打开自定义的Activity
        	Intent i = new Intent(context, MainActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
			Intent activityIntent = new Intent(context, MainActivity.class);
			//  要想在Service中启动Activity，必须设置如下标志
			activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(activityIntent);

//			String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			NewMessage mNewMessage = JsonParseUtil.toBeanByJson(JsonParseUtil.getJsonByString(extra).getAsJsonObject(), NewMessage.class);
//            Log.i("main",mNewMessage.getTime());
//			new Thread(){
//				@Override
//				public void run() {
//
//				}
//			}.start();
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					if (json.has("type")){
						if ("1".equals(json.getString("type"))){
							NewMessage mNewMessage = new NewMessage();
							mNewMessage.setContent(json.getString("content"));
							mNewMessage.setSee(false);
							mNewMessage.setTime(json.getString("time"));
							mNewMessage.setTitle(json.getString("title"));
							LocalUtils LocalUtils = new LocalUtils(LeeApplication.newMessagePath);
							ArrayList<NewMessage> mList = (ArrayList<NewMessage>) LocalUtils.read();
							mList.add(mNewMessage);
							LocalUtils.put(mList);
						}else if ("2".equals(json.getString("type"))){
							MyMessage mMyMessage = new MyMessage();
							mMyMessage.setTime(json.getString("time"));
							mMyMessage.setContent(json.getString("content"));
							mMyMessage.setmIsRead(false);
							LocalUtils LocalUtils = new LocalUtils(LeeApplication.myMessagePath);
							ArrayList<MyMessage> mList = (ArrayList<MyMessage>) LocalUtils.read();
							mList.add(mMyMessage);
							LocalUtils.put(mList);
						}
					}


//					while (it.hasNext()) {
//						String myKey = it.next().toString();
//
//						sb.append("\nkey:" + key + ", value: [" +
//								myKey + " - " +json.optString(myKey) + "]");
//					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
	}
}
