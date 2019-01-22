
package com.smart.service;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import java.util.List;

public interface BookService {
    List<Book> getList();

    PageInfo<Book> pageBook(Book var1, Integer var2, Integer var3);

    boolean addBook(Book var1);

    boolean isIdExist(String var1);

    boolean isNameExist(String var1);

    boolean checkBook(Record var1);

    PageInfo<Record> pageRecord(Record var1, Integer var2, Integer var3);

    PageInfo<Record> pageRecord1(Record var1, Integer var2, Integer var3, String var4);

    boolean backBook(Record var1);

    String backBookByCode(String var1);

    String passBookByCode(String var1);

    boolean delRecord(int var1);

    boolean delBook(String var1);

    boolean passByCode(int[] var1, String[] var2, String[] var3);

    boolean pass(int[] var1);

    boolean back(int[] var1);
}
