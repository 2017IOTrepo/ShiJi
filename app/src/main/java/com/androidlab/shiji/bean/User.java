package com.androidlab.shiji.bean;

/**
 * 这里采用枚举单例模式
 * 使用时请使用User.INSTANCE.getInstance()获取实例
 * */
public enum User {
    INSTANCE;
    User(){
        Name = "请注册!";
        Email = "null";
    }
    public byte Id; // 这里用byte代替uint
    public String Email;
    public String Name;
    public String Password;
}