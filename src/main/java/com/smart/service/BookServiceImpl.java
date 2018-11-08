package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.dao.BookDao;
import com.smart.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;
    @Autowired
    UserDao userDao;
    @Autowired
    com.smart.redis.JedisClient JedisClient;

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
    @Transactional(rollbackFor={Exception.class})
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
            if(book!=null){
                JedisClient.setObject(bookId,(Object) book);
            }
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
            if(book!=null){
                JedisClient.setObject(bookName,(Object)book);
            }
        }
        if(StringUtils.isEmpty(book)){
            isSuccess = true;
        }
        return isSuccess;
    }

    //登记
    @Transactional(rollbackFor={Exception.class})
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


    //查询所有借阅列表
    public PageInfo<Record> pageRecord(Record record, Integer pageNum, Integer pageSize) {
        PageInfo<Record> page = null;
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> map = new HashMap<String,Object>();
        List<Record> uList = bookDao.listRecord(record);
        page = new PageInfo<Record>(uList);
        return page;
    }

    //查询部分借阅列表
    public PageInfo<Record> pageRecord1(Record record, Integer pageNum, Integer pageSize,String geNumber) {
        PageInfo<Record> page = null;
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("bookId",record.getBookId());
        map.put("state",record.getState());
        map.put("geNumber",geNumber);
        List<Record> uList = bookDao.listRecordByNumber(map);
        page = new PageInfo<Record>(uList);
        return page;
    }

    //还书登记
    @Transactional(rollbackFor={Exception.class})
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
    @Transactional(rollbackFor={Exception.class})
    public boolean delRecord(int id) {
        boolean isSuccess = false;
        bookDao.delRecord(id);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delBook(int id) {
        boolean isSuccess = false;
        bookDao.delBook(id);
        isSuccess = true;
        return isSuccess;
    }
}
