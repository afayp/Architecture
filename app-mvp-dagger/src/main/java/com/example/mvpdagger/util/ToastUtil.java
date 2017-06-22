package com.example.mvpdagger.util;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtils
 *
 *
 */
public class ToastUtil {
    private Context mContext;
    private static ToastUtil mInstance;
    private Toast mToast;

//    public static ToastUtils getInstance() {
//        return mInstance;
//    }
//
//    public static void initialize(Context ctx) {
//        mInstance = new ToastUtils(ctx);
//    }

    public ToastUtil(Context ctx) {
        mContext = ctx;
    }

    public void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
