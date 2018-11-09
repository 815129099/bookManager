package com.smart.bean;

import java.io.Serializable;

public class Book implements Serializable {

    /**
     * 使用jedis 需要序列化接口
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private int bookNumber;
    private String bookId;
    private String bookName;
    private int lendNumber;
    private String bookLocation;
    private int bookState;

    public int getBookState() {
        return bookState;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }

    public int getId() {
        return id;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public String getBookName() {
        return bookName;
    }



    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public int getLendNumber() {
        return lendNumber;
    }

    public void setLendNumber(int lendNumber) {
        this.lendNumber = lendNumber;
    }

    public void setId(int id) {
        this.id = id;
    }
}
