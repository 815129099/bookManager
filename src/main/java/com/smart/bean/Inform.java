package com.smart.bean;

import java.io.Serializable;

public class Inform implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String geNumber;
    private String title;
    private String detail;
    private String createTime;
    private String updateTime;
    private int informState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInformState() {
        return informState;
    }

    public void setInformState(int informState) {
        this.informState = informState;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDetail() {

        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeNumber() {

        return geNumber;
    }

    public void setGeNumber(String geNumber) {
        this.geNumber = geNumber;
    }
}
