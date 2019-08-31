package com.androidlab.shiji.bean;

import java.io.Serializable;

public class Sci_History_Show implements Serializable {
    private int tv_top;
    private int iv_show;
    private int tv_des;

    public Sci_History_Show(int tv_top, int tv_des, int iv_show){
        this.tv_des = tv_des;
        this.iv_show = iv_show;
        this.tv_top = tv_top;

    }


    public int getTv_top() {
        return tv_top;
    }

    public void setTv_top(int tv_top) {
        this.tv_top = tv_top;
    }

    public int getIv_show() {
        return iv_show;
    }

    public void setIv_show(int iv_show) {
        this.iv_show = iv_show;
    }

    public int getTv_des() {
        return tv_des;
    }

    public void setTv_des(int tv_des) {
        this.tv_des = tv_des;
    }
}
