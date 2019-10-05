package com.androidlab.shiji.bean;

/**
 * 这里采用枚举单例模式
 * 使用时请使用User.INSTANCE.getInstance()获取实例
 * */
public enum User {
    INSTANCE;
    private UserIns userIns;
    User() {
        userIns = new UserIns();
    }

    public UserIns getUserIns() {
        return userIns;
    }
}
