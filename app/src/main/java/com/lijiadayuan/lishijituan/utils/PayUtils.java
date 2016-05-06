package com.lijiadayuan.lishijituan.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

/**
 * Created by zhaoyi on 16/5/4.
 * 支付相关的util类
 */
public class PayUtils {
    // 商户PID
    public static final String PARTNER = "2088221309346686";
    // 商户收款账号
    public static final String SELLER = "bjlijiadayuan@sina.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNA" +
            "DCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    private static final int SDK_PAY_FLAG = 1;

    private PayCallback mPayCallback;


    /**
     * 必须在线程里调用
     * @param mContext
     * @param info
     * @param mPayCallback
     */
    public void aliPay(Activity mContext,String info,PayCallback mPayCallback){
        PayTask alipay = new PayTask(mContext);
        String result = alipay.pay(info,true);
        mPayCallback.setResult(result);
    }
}
