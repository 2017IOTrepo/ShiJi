package com.androidlab.shiji.bean;

import net.sf.json.JSONObject;

public class Msg {
    public int code;
    public JSONObject data;

    public Msg(int code, JSONObject data) {
        this.code = code;
        this.data = data;
    }
}
