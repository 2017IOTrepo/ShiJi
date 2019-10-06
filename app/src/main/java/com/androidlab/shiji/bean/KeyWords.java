package com.androidlab.shiji.bean;

import java.io.PipedReader;
import java.io.Serializable;

public class KeyWords implements Serializable {

    private String keyword;

    @Override
    public String toString() {
        return "KeyWords{" +
                "keyword='" + keyword + '\'' +
                '}';
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
