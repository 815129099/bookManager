package com.smart.dao;

import com.smart.bean.Book;
import com.smart.bean.Record;

import java.util.List;

public interface bookDao {

    public List<Book> getList();
    //书籍列表
    public List<Book> listBook(Book book);
    //添加书籍
    public void addBook(Book book);
    //通过Id获取书籍
    public Book getBookById(String bookId);
    //通过书名获取书籍
    public Book getBookByName(String bookName);
    //控制书籍数量
    public void checkBook(String bookId);
    //记录借阅人信息
    public void record(Record record);
    //借阅列表
    public List<Record> listRecord(Record record);
    //还书登记
    public void addNumber(String bookId);
    public void backTime(Record record);

}
