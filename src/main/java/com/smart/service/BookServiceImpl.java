package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.BookDao;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
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

    //申请借阅

    public boolean checkBook(Record record) {
        boolean isSuccess = false;
        System.out.println("工号为"+record.getGeNumber());
        System.out.println("工号为"+record.getBookId());
        //获取Book
        Book book = bookDao.getBookById(record.getBookId());
        System.out.println("bookName："+book.getBookName());
        record.setBookName(book.getBookName());
        System.out.println("bookName："+record.getBookName());


        System.out.println("12312123");
        //借书人名字为空则通过工号查询
        if(record.getGeName()==null || record.getGeName()==""){
            System.out.println("222222222");
            User user = userDao.findByGeNumber(record.getGeNumber());
            System.out.println("用户名"+user.getGeName());
            record.setGeName(user.getGeName());
            System.out.println("用户名:::"+record.getGeName());
        }

        if(book.getLendNumber()>0){
            //书籍数量减1
            bookDao.checkBook(record.getBookId());
        }else {
            return false;
        }
        record.setApplyTime(DateUtil.getDate());//申请时间
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
        System.out.println(record.getLendTime()+","+record.getApplyTime());
        map.put("lendTime",record.getLendTime());
        map.put("applyTime",record.getApplyTime());
        List<Record> uList = bookDao.listRecordByNumber(map);
        page = new PageInfo<Record>(uList);
        return page;
    }

    //还书登记
    @Transactional(rollbackFor={Exception.class})
    public boolean backBook(Record record) {
        boolean isSuccess = false;
        record.setBackTime(DateUtil.getDate());
        bookDao.addNumber(record.getBookId());
        bookDao.backTime(record);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delRecord(int id) {
        boolean isSuccess = false;
        Record record = bookDao.getRecordById(id);

        bookDao.delRecord(id);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delBook(String bookId) {
        boolean isSuccess = false;
        bookDao.delBook(bookId);
        isSuccess = true;
        return isSuccess;
    }

    //批准申请
    @Transactional(rollbackFor={Exception.class})
    @Override
    public boolean pass(int[] arr) {
        boolean isSuccess = false;
        for (int id:arr) {
            bookDao.pass(id,DateUtil.getFour());
        }
        isSuccess = true;
        return isSuccess;
    }

    //退回申请
    @Transactional(rollbackFor={Exception.class})
    @Override
    public boolean back(int[] arr) {
        boolean isSuccess = false;
        for (int id:arr) {
            bookDao.back(id);
            bookDao.addNumber(bookDao.getRecordById(id).getBookId());
        }
        isSuccess = true;
        return isSuccess;
    }
}
