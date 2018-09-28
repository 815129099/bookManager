package com.smart.controller;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class bookController {

    @Autowired
    private bookService bookService;

    @RequestMapping(value="/index")
    public ModelAndView index() {
        return new ModelAndView("index1");
    }

    @RequestMapping(value="/maSystem")
    public ModelAndView maSystem() {
        return new ModelAndView("maSystem");
    }

    @RequestMapping(value="/meList")
    public ModelAndView meList() {
        return new ModelAndView("meList");
    }

    @RequestMapping(value="/allegeList")
    public ModelAndView allegeList() {
        return new ModelAndView("allegeList");
    }

    @RequestMapping(value="/login")
    public ModelAndView login() {
        return new ModelAndView("log");
    }

    // 用户列表
    @RequestMapping("/bookList")
    @ResponseBody
    public Map<String, Object> bookList() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Book> list = bookService.getList();
        for (Book b :
                list) {
            System.out.println(b.getBookId()+","+b.getBookName());
        }
        map.put("list",list);
        return map;
    }

    //查询书籍列表
    @RequestMapping("/listBook.do")
    @ResponseBody
    public Map<String,Object> listBook(Book book,Integer pageNum,Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
            //获取分页信息
            if (pageNum == null || pageNum == 0) {
                pageNum = 1;
            }
            if (pageSize == null) {
                pageSize = 15;
            }
            if (book.getBookName() != null) {
                book.setBookName("%" + book.getBookName() + "%");
            }
            if(book.getBookLocation() != null){
                book.setBookLocation("%" + book.getBookLocation() + "%");
            }
            PageInfo<Book> page = bookService.pageBook(book, pageNum, pageSize);
            map.put("page", page);

        return map;
    }

    //查询书籍列表
    @RequestMapping("/listRecord.do")
    @ResponseBody
    public Map<String,Object> listRecord(Record record,Integer pageNum,Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        //获取分页信息
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if(record.getGeNumber() != null){
            record.setGeNumber("%" + record.getGeNumber() + "%");
        }
        PageInfo<Record> page = bookService.pageRecord(record, pageNum, pageSize);
        map.put("page", page);

        return map;
    }
    //添加书籍
    @RequestMapping("/addBook.do")
    @ResponseBody
    public Map<String,Object> addBook(Book book) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.addBook(book);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    //登记
    @RequestMapping("/checkBook.do")
    @ResponseBody
    public Map<String,Object> checkBook(Record record) throws IOException {
        if(!StringUtils.isEmpty(record)){
            System.out.print(record.getBookId()+","+record.getGeName()+","+record.getGeNumber()+","+record.getPhone());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.checkBook(record);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    // 判断书籍代码是否已存在
    @RequestMapping("/isIdExist.do")
    @ResponseBody
    public Map<String, Object> isIdExist(String bookId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isSuccess = bookService.isIdExist(bookId);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    // 判断书籍名称是否已存在
    @RequestMapping("/isNameExist.do")
    @ResponseBody
    public Map<String, Object> isNameExist(String bookName) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isSuccess = bookService.isNameExist(bookName);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }
        return result;
    }

    // 还书登记
    @RequestMapping("/backBook.do")
    @ResponseBody
    public Map<String, Object> backBook(Record record) throws Exception {
        if(!StringUtils.isEmpty(record)){
            System.out.println(record.getDescription()+","+record.getBookId()+","+record.getGeNumber());

        }
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isSuccess = bookService.backBook(record);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }
        return result;
    }

    //添加书籍
    @RequestMapping("/delRecord.do")
    @ResponseBody
    public Map<String,Object> delRecord(Record record) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.delRecord(record);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }


}
