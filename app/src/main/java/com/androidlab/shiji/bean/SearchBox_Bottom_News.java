package com.androidlab.shiji.bean;

import java.io.Serializable;

public class SearchBox_Bottom_News implements Serializable {

    // 新闻标题，内容，图片
    private String title;
    private String desc;
    private int photoId;

    public SearchBox_Bottom_News(String title, String desc, int photoId) {
        super();
        this.title = title;
        this.desc = desc;
        this.photoId = photoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
