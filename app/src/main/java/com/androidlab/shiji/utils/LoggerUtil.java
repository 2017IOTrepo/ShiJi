package com.androidlab.shiji.utils;

import android.util.Log;

public class LoggerUtil {
    public static void LogD(String tag, String content) {
        Log.d(tag, content);
        return;
    }

    public static void LogI(String tag, String content) {
        Log.i(tag, content);
        return;
    }
}
