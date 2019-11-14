package com.androidlab.shiji.bean;

public class MapContent {
    int num;
    int image;
    String title;
    String content;
    String autherName;

    double autherLong; //经度
    double autherLati; //纬度

    public MapContent(String title, String autherName, double autherLong, double autherLati) {
        this.title = title;
        this.autherName = autherName;
        this.autherLong = autherLong;
        this.autherLati = autherLati;
    }

    public MapContent(int num, int image, String title, String content) {
        this.num = num;
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
