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

class UserIns {
    private String Id;
    private String Email;
    private String Password;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
