package com.androidlab.shiji.bean;

import java.io.Serializable;

public class SearchBox_Bottom_News implements Serializable {

    // 新闻标题，内容，图片, 打开的网页
    private String title;
    private String desc;
    private int photoId;

    private String Uri;

    private int talk;
    private int pick;



    public SearchBox_Bottom_News(String title, String desc, int photoId, String Uri, int talk, int pick) {
        super();
        this.title = title;
        this.desc = desc;
        this.photoId = photoId;
        this.Uri = Uri;
        this.talk = talk;
        this.pick = pick;
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

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public int getTalk() {
        return talk;
    }

    public void setTalk(int talk) {
        this.talk = talk;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }
}
