package com.smart.bean;

public class AccessRecord {
    private int id;
    private String sessionId;
    private String ipNumber;
    private String geNumber;
    private String beginTime;
    private String endTime;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getGeNumber() {
        return geNumber;
    }

    public void setGeNumber(String geNumber) {
        this.geNumber = geNumber;
    }

    public String getIpNumber() {
        return ipNumber;
    }

    public void setIpNumber(String ipNumber) {
        this.ipNumber = ipNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
