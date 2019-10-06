package com.androidlab.shiji.utils;

import android.content.Context;
import android.content.Intent;

import com.androidlab.shiji.activity.WebUIActivity;
import com.androidlab.shiji.bean.Msg;

import net.sf.json.JSONObject;

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


    public static Msg msgGetter(String body) {
        JSONObject jsonObject = JSONObject.fromObject(body);
        return new Msg(jsonObject.getInt("code"), jsonObject.getJSONObject("data"));
    }
}
