package com.smart.service;


import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;

import java.util.List;

public interface BookService {
    public List<Book> getList();
    //查询书籍列表
    public PageInfo<Book> pageBook(Book book, Integer pageNum, Integer pageSize);
    //添加书籍
    public boolean addBook(Book book);
    //判断书籍代码是否已存在
    public boolean isIdExist(String bookId);
    //判断书籍名称是否已存在
    public boolean isNameExist(String bookName);
    //登记
    public boolean checkBook(Record record);
    //查询借阅列表
    public PageInfo<Record> pageRecord(Record record, Integer pageNum, Integer pageSize);
    //查询借阅列表
    public PageInfo<Record> pageRecord1(Record record, Integer pageNum, Integer pageSize,String geNumber);
    //还书登记
    public boolean backBook(Record record);
    //删除借书记录
    public boolean delRecord(int id);
    public boolean delBook(String bookId);


}
