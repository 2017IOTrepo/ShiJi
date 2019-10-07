package com.androidlab.shiji.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LoggerUtil {
    public static void logD(String tag, String content) {
        Log.d(tag, content);
        return;
    }

    public static void logI(String tag, String content) {
        Log.i(tag, content);
        return;
    }

    public static void showToastShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        return;
    }

    public static void showToastLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        return;
    }
}
