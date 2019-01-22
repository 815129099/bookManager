package com.smart.dao;

import com.smart.bean.Book;
import com.smart.bean.Record;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface BookDao {
    List<Book> getList();

    List<Book> listBook(Book var1);

    void addBook(Book var1);

    Book getBookById(String var1);

    Book getBookByName(String var1);

    void checkBook(String var1);

    void record(Record var1);

    List<Record> listRecord(Record var1);

    List<Record> listRecordByNumber(Map<String, Object> var1);

    void addNumber(String var1);

    void backTime(Record var1);

    void delRecord(int var1);

    void delBook(String var1);

    Record getRecordById(int var1);

    void pass(@Param("id") int var1, @Param("lendTime") String var2);

    void back(int var1);

    List<Map<String, Object>> getRecordList();

    void insertCode(@Param("bookId") String var1, @Param("bookCode") String var2);

    void backBookByCode(@Param("backTime") String var1, @Param("bookCode") String var2);

    void passBookByCode(@Param("lendTime") String var1, @Param("bookCode") String var2);

    Record getRecordByCode(@Param("bookCode") String var1, @Param("state") String var2);

    void updatePass(List<Record> var1);

    void updateBookCode(List<Book> var1);

    List<Object> getDataByDbName(@Param("dbName") String var1);

    int getCountBookNumber(@Param("geNumber") String var1);
}
