package com.androidlab.shiji.bean;

public class UserIns {
    private byte Id; // 这里用byte代替uint
    private String Email;
    private String Password;

    public byte getId() {
        return Id;
    }

    public void setId(byte id) {
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
