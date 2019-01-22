package com.smart.bean;

public class MoneyRecord {
    private int id;
    private String geNumber;
    private String moneyNow;
    private String moneyBefore;
    private String account;
    private String moneyTime;
    private String reason;

    public MoneyRecord() {
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoneyTime() {
        return this.moneyTime;
    }

    public void setMoneyTime(String moneyTime) {
        this.moneyTime = moneyTime;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMoneyBefore() {
        return this.moneyBefore;
    }

    public void setMoneyBefore(String moneyBefore) {
        this.moneyBefore = moneyBefore;
    }

    public String getMoneyNow() {
        return this.moneyNow;
    }

    public void setMoneyNow(String moneyNow) {
        this.moneyNow = moneyNow;
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
