package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.dao.bookDao;
import com.smart.redis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class bookServiceImp implements bookService {
    @Autowired
    bookDao bookDao;
    @Autowired
    JedisClient JedisClient;

    public List<Book> getList() {
        List<Book> list = null;
        list = (List<Book>) JedisClient.getList("bookList");
        if(list == null || list.isEmpty()){
            list = bookDao.getList();
            JedisClient.setList("bookList",list);
        }
        return list;
    }

    //查询书籍列表
    public PageInfo<Book> pageBook(Book book, Integer pageNum, Integer pageSize) {
        PageInfo<Book> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Book> uList = bookDao.listBook(book);
        page = new PageInfo<Book>(uList);
        return page;
    }

    //添加书籍
    public boolean addBook(Book book) {
        boolean isSuccess = false;
        bookDao.addBook(book);
        isSuccess = true;
        return isSuccess;
    }

    //判断书籍代码是否已存在
    public boolean isIdExist(String bookId){
        boolean isSuccess = false;
        Book book = null;
        book = (Book)JedisClient.getObject(bookId);
        if(book==null){
            book = bookDao.getBookById(bookId);
            JedisClient.setObject(bookId,book);
        }

        if(StringUtils.isEmpty(book)){
            isSuccess = true;
        }
        return isSuccess;
    }

    //判断书籍名称是否已存在
    public boolean isNameExist(String bookName){
        boolean isSuccess = false;
        Book book = null;
        book = (Book)JedisClient.getObject(bookName);
        if(book==null){
            book = bookDao.getBookByName(bookName);
            JedisClient.setObject(bookName,book);
        }
        if(StringUtils.isEmpty(book)){
            isSuccess = true;
        }
        return isSuccess;
    }

    //登记
    public boolean checkBook(Record record) {
        boolean isSuccess = false;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        bookDao.checkBook(record.getBookId());
        record.setLendTime(ft.format(dNow));
        bookDao.record(record);
        isSuccess = true;
        return isSuccess;
    }


    //查询借阅列表
    public PageInfo<Record> pageRecord(Record record, Integer pageNum, Integer pageSize) {
        PageInfo<Record> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Record> uList = bookDao.listRecord(record);
        page = new PageInfo<Record>(uList);
        return page;
    }

    //还书登记
    public boolean backBook(Record record) {
        boolean isSuccess = false;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        record.setBackTime(ft.format(dNow));
        System.out.println(record.getGeNumber()+","+record.getBookId()+","+record.getBackTime());
        bookDao.addNumber(record.getBookId());
        bookDao.backTime(record);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public boolean delRecord(Record record) {
        return false;
    }
}
