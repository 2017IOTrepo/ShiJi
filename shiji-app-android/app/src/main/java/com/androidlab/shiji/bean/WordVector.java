package com.androidlab.shiji.bean;

public class WordVector {
    public String wordContent;
    public String probability;

    public WordVector(String wordContent, String probability) {
        this.wordContent = wordContent;
        this.probability = probability;
    }
}
