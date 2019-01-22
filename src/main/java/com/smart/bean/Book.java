package com.smart.bean;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int bookNumber;
    private String bookId;
    private String bookName;
    private int lendNumber;
    private String bookLocation;
    private int bookState;
    private String bookCode;

    public Book() {
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public int getBookState() {
        return this.bookState;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }

    public int getId() {
        return this.id;
    }

    public int getBookNumber() {
        return this.bookNumber;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String getBookLocation() {
        return this.bookLocation;
    }

    public String getBookName() {
        return this.bookName;
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
        return this.lendNumber;
    }

    public void setLendNumber(int lendNumber) {
        this.lendNumber = lendNumber;
    }

    public void setId(int id) {
        this.id = id;
    }
}
