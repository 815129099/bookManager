
package com.smart.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.dao.BookDao;
import com.smart.dao.UserDao;
import com.smart.redis.DateUtil;
import com.smart.redis.JedisClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;
    @Autowired
    UserDao userDao;
    @Autowired
    JedisClient JedisClient;

    public BookServiceImpl() {
    }

    public List<Book> getList() {
        return this.bookDao.getList();
    }

    public PageInfo<Book> pageBook(Book book, Integer pageNum, Integer pageSize) {
        PageInfo<Book> page = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Book> uList = this.bookDao.listBook(book);
        page = new PageInfo(uList);
        return page;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean addBook(Book book) {
        boolean isSuccess = false;
        this.bookDao.addBook(book);
        isSuccess = true;
        return isSuccess;
    }

    public boolean isIdExist(String bookId) {
        boolean isSuccess = false;
        Book book = null;
        book = (Book)this.JedisClient.getObject(bookId);
        if (book == null) {
            book = this.bookDao.getBookById(bookId);
            if (book != null) {
                this.JedisClient.setObject(bookId, book);
            }
        }

        if (StringUtils.isEmpty(book)) {
            isSuccess = true;
        }

        return isSuccess;
    }

    public boolean isNameExist(String bookName) {
        boolean isSuccess = false;
        Book book = null;
        book = (Book)this.JedisClient.getObject(bookName);
        if (book == null) {
            book = this.bookDao.getBookByName(bookName);
            if (book != null) {
                this.JedisClient.setObject(bookName, book);
            }
        }

        if (StringUtils.isEmpty(book)) {
            isSuccess = true;
        }

        return isSuccess;
    }

    public boolean checkBook(Record record) {
        boolean isSuccess = false;
        int num = this.bookDao.getCountBookNumber(record.getGeNumber());
        if (num >= 4) {
            return isSuccess;
        } else {
            Book book = this.bookDao.getBookById(record.getBookId());
            record.setBookName(book.getBookName());
            if (record.getGeName() == null || record.getGeName() == "") {
                User user = this.userDao.findByGeNumber(record.getGeNumber());
                record.setGeName(user.getGeName());
            }

            if (book.getLendNumber() > 0) {
                this.bookDao.checkBook(record.getBookId());
                record.setApplyTime(DateUtil.getDate());
                this.bookDao.record(record);
                isSuccess = true;
                return isSuccess;
            } else {
                return false;
            }
        }
    }

    public PageInfo<Record> pageRecord(Record record, Integer pageNum, Integer pageSize) {
        PageInfo<Record> page = null;
        PageHelper.startPage(pageNum, pageSize);
        new HashMap();
        List<Record> uList = this.bookDao.listRecord(record);
        page = new PageInfo(uList);
        return page;
    }

    public PageInfo<Record> pageRecord1(Record record, Integer pageNum, Integer pageSize, String geNumber) {
        PageInfo<Record> page = null;
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap();
        map.put("bookId", record.getBookId());
        map.put("state", record.getState());
        map.put("geNumber", geNumber);
        System.out.println(record.getLendTime() + "," + record.getApplyTime());
        map.put("lendTime", record.getLendTime());
        map.put("applyTime", record.getApplyTime());
        List<Record> uList = this.bookDao.listRecordByNumber(map);
        page = new PageInfo(uList);
        return page;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean backBook(Record record) {
        boolean isSuccess = false;
        record.setBackTime(DateUtil.getDate());
        this.bookDao.addNumber(record.getBookId());
        this.bookDao.backTime(record);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public String backBookByCode(String bookCode) {
        System.out.println(bookCode);
        String isSuccess = "2";
        Record record = this.bookDao.getRecordByCode(bookCode, "借阅");
        if (StringUtils.isEmpty(record)) {
            isSuccess = "1";
        } else {
            this.bookDao.backBookByCode(DateUtil.getDate(), bookCode);
            isSuccess = "0";
        }

        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public String passBookByCode(String bookCode) {
        System.out.println(bookCode);
        String isSuccess = "2";
        Record record = this.bookDao.getRecordByCode(bookCode, "申请");
        if (StringUtils.isEmpty(record)) {
            isSuccess = "1";
        } else {
            this.bookDao.passBookByCode(DateUtil.getFour(), bookCode);
            isSuccess = "0";
        }

        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean delRecord(int id) {
        boolean isSuccess = false;
        this.bookDao.getRecordById(id);
        this.bookDao.delRecord(id);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean delBook(String bookId) {
        boolean isSuccess = false;
        this.bookDao.delBook(bookId);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean passByCode(int[] arr, String[] arrId, String[] arrCode) {
        boolean isSuccess = false;
        ArrayList<Record> listRecord = new ArrayList();
        ArrayList<Book> listBook = new ArrayList();
        String d = DateUtil.getFour();

        Record r;
        for(int i = 0; i < arr.length; ++i) {
            r = new Record();
            Book book = new Book();
            r.setId(arr[i]);
            r.setLendTime(d);
            listRecord.add(i, r);
            book.setBookCode(arrCode[i]);
            book.setBookId(arrId[i]);
            listBook.add(i, book);
        }

        Iterator var11 = listBook.iterator();

        while(var11.hasNext()) {
            Book i = (Book)var11.next();
            System.out.println(i.getBookCode() + "," + i.getBookId());
        }

        var11 = listRecord.iterator();

        while(var11.hasNext()) {
            r = (Record)var11.next();
            System.out.println(r.getLendTime() + "," + r.getId());
        }

        this.bookDao.updatePass(listRecord);
        this.bookDao.updateBookCode(listBook);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean pass(int[] arr) {
        boolean isSuccess = false;
        ArrayList<Record> listRecord = new ArrayList();
        String d = DateUtil.getFour();

        Record r;
        for(int i = 0; i < arr.length; ++i) {
            r = new Record();
            r.setId(arr[i]);
            r.setLendTime(d);
            listRecord.add(i, r);
        }

        Iterator var7 = listRecord.iterator();

        while(var7.hasNext()) {
            r = (Record)var7.next();
            System.out.println(r.getLendTime() + "," + r.getId());
        }

        this.bookDao.updatePass(listRecord);
        isSuccess = true;
        return isSuccess;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean back(int[] arr) {
        boolean isSuccess = false;
        int[] var3 = arr;
        int var4 = arr.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            this.bookDao.back(id);
            this.bookDao.addNumber(this.bookDao.getRecordById(id).getBookId());
        }

        isSuccess = true;
        return isSuccess;
    }
}
