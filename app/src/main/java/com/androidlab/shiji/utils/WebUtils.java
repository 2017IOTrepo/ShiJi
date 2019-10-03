package com.androidlab.shiji.utils;

import android.content.Context;
import android.content.Intent;

import com.androidlab.shiji.activity.WebUIActivity;

public class WebUtils {

    /**
     *打开网页
     * @param
     */


    public static void pickWeb(Context context,String Title, String Uri ){
        Intent intent = new Intent(context, WebUIActivity.class);
        intent.putExtra("title", Title);
        intent.putExtra("url", Uri);
        context.startActivity(intent);
    }
}
