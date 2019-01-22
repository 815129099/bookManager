package com.smart.bean;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String geNumber;
    private String geName;
    private String password;
    private String role;
    private String authority;
    private String userState;
    private String createTime;
    private String updateTime;
    private String phone;
    private String email;
    private String userMoney;

    public User() {
    }

    public String getUserMoney() {
        return this.userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeName() {
        return this.geName;
    }

    public void setGeName(String geName) {
        this.geName = geName;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserState() {
        return this.userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGeNumber() {
        return this.geNumber;
    }

    public void setGeNumber(String geNumber) {
        this.geNumber = geNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
