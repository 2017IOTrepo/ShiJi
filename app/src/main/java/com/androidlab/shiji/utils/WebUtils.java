package com.androidlab.shiji.utils;

import android.content.Context;
import android.content.Intent;

import com.androidlab.shiji.activity.WebUIActivity;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;

import net.sf.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
