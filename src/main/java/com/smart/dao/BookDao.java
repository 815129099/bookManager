package com.smart.dao;

import com.smart.bean.Book;
import com.smart.bean.Record;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookDao {

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
    //所有借阅列表
    public List<Record> listRecord(Record record);
    //所有借阅列表
    public List<Record> listRecordByNumber(Map<String,Object> map);
    //还书登记
    public void addNumber(String bookId);
    public void backTime(Record record);
    public void delRecord(int id);
    public void delBook(String bookId);
    public Record getRecordById(int id);
    //批准申请
    public void pass(@Param("id") int id,@Param("lendTime") String lendTime);
    public void back(int id);
    //获取record到期的记录
    public List<Map<String,Object>> getRecordList();
}
