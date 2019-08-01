package com.androidlab.shiji.bean;

import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class Sci_History_Show implements Serializable {
    private String tv_top;
    private int iv_show;
    private String tv_des;

    public Sci_History_Show(String tv_top, String tv_des,int iv_show){
        this.tv_des = tv_des;
        this.iv_show = iv_show;
        this.tv_top = tv_top;

    }


    public String getTv_top() {
        return tv_top;
    }

    public void setTv_top(String tv_top) {
        this.tv_top = tv_top;
    }

    public int getIv_show() {
        return iv_show;
    }

    public void setIv_show(int iv_show) {
        this.iv_show = iv_show;
    }

    public String getTv_des() {
        return tv_des;
    }

    public void setTv_des(String tv_des) {
        this.tv_des = tv_des;
    }
}
