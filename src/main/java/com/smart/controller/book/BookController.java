package com.smart.controller.book;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.redis.ExcelParam;
import com.smart.redis.ExcelUtil;
import com.smart.redis.JedisClient;
import com.smart.redis.ExcelParam.Builder;
import com.smart.service.BookService;
import com.smart.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/"})
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    JedisClient JedisClient;

    public BookController() {
    }

    @RequestMapping({"/introduce.do"})
    public ModelAndView introduce() {
        return new ModelAndView("book/introduce");
    }

    @RequestMapping({"/index.do"})
    public ModelAndView index() {
        return new ModelAndView("index1");
    }

    @RequestMapping({"/maSystem.do"})
    public ModelAndView maSystem() {
        return new ModelAndView("book/maSystem");
    }

    @RequestMapping({"/meList.do"})
    public ModelAndView meList() {
        return new ModelAndView("book/meList");
    }

    @RequestMapping({"/allegeList.do"})
    public ModelAndView allegeList(String geNumber, String bookId) {
        ModelAndView modelAndView = new ModelAndView("book/allegeList");
        if (!StringUtils.isEmpty(geNumber) && StringUtils.isEmpty(bookId)) {
            System.out.println(geNumber);
            modelAndView.addObject("geNumber", geNumber);
        } else if (StringUtils.isEmpty(geNumber) && !StringUtils.isEmpty(bookId)) {
            System.out.println(bookId);
            modelAndView.addObject("bookId", bookId);
        }

        return modelAndView;
    }
/*
    @RequestMapping({"/bookList.do"})
    @ResponseBody
    public Map<String, Object> bookList() throws Exception {
        Map<String, Object> map = new HashMap();
        List<Book> list = this.bookService.getList();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Book b = (Book)var3.next();
            System.out.println(b.getBookId() + "," + b.getBookName());
        }

        map.put("list", list);
        return map;
    }*/

    @RequestMapping({"/listBook.do"})
    @ResponseBody
    public Map<String, Object> listBook(Book book, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (book.getBookName() != null) {
            book.setBookName(book.getBookName() + "%");
        }

        if (book.getBookLocation() != null) {
            book.setBookLocation("%" + book.getBookLocation() + "%");
        }

        PageInfo<Book> page = this.bookService.pageBook(book, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping({"/listRecord.do"})
    @ResponseBody
    public Map<String, Object> listRecord(Record record, Integer pageNum, Integer pageSize) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        Set<String> set = this.userService.findRoles(geNumber);
        String role = "";
        if (!set.isEmpty()) {
            role = (String)set.iterator().next();
            System.out.println(role);
        }

        PageInfo page;
        if (role.equals("admin")) {
            if (record.getGeNumber() != null) {
                record.setGeNumber("%" + record.getGeNumber() + "%");
            }

            page = this.bookService.pageRecord(record, pageNum, pageSize);
            map.put("page", page);
        } else {
            page = this.bookService.pageRecord1(record, pageNum, pageSize, geNumber);
            map.put("page", page);
        }

        return map;
    }

    @RequestMapping(
            value = {"/Book.do"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> addBook(Book book) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.addBook(book);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping(
            value = {"/Record.do"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> checkBook(Record record) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        if (record.getGeNumber() == null || record.getGeNumber() == "") {
            record.setGeNumber(geNumber);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.checkBook(record);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/isIdExist.do"})
    @ResponseBody
    public Map<String, Object> isIdExist(String bookId) throws Exception {
        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.bookService.isIdExist(bookId);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    @RequestMapping({"/isNameExist.do"})
    @ResponseBody
    public Map<String, Object> isNameExist(String bookName) throws Exception {
        Map<String, Object> result = new HashMap();
        System.out.println(bookName);
        boolean isSuccess = this.bookService.isNameExist(bookName);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    @RequestMapping(
            value = {"/Record.do"},
            method = {RequestMethod.PUT}
    )
    @ResponseBody
    public Map<String, Object> backBook(@RequestBody Record record) throws Exception {
        if (!StringUtils.isEmpty(record)) {
            System.out.println(record.getDescription() + "," + record.getBookId() + "," + record.getGeNumber());
        }

        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.bookService.backBook(record);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    @RequestMapping(
            value = {"/Record.do"},
            method = {RequestMethod.DELETE}
    )
    @ResponseBody
    public Map<String, Object> delRecord(@RequestBody Record record) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.delRecord(record.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping(
            value = {"/Book.do"},
            method = {RequestMethod.DELETE}
    )
    @ResponseBody
    public Map<String, Object> delBook(@RequestBody Book book) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.delBook(book.getBookId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/passByCode.do"})
    @ResponseBody
    public Map<String, Object> pass(int[] arr, String[] arrCode, String[] arrId) throws IOException {
        for(int i = 0; i < arr.length; ++i) {
            System.out.println(arr[i] + "," + arrCode[i] + "," + arrId[i]);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.passByCode(arr, arrId, arrCode);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/pass.do"})
    @ResponseBody
    public Map<String, Object> pass(int[] arr) throws IOException {
        for(int i = 0; i < arr.length; ++i) {
            System.out.println(arr[i]);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.pass(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/back.do"})
    @ResponseBody
    public Map<String, Object> back(int[] arr) throws IOException {
        int[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            System.out.println(id);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.bookService.back(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/backBookByCode.do"})
    @ResponseBody
    public Map<String, Object> backBookByCode(String bookCode) throws IOException {
        Map<String, Object> map = new HashMap();
        String isSuccess = this.bookService.backBookByCode(bookCode);
        map.put("tip", isSuccess);
        return map;
    }

    @RequestMapping({"/passBookByCode.do"})
    @ResponseBody
    public Map<String, Object> passBookByCode(String bookCode) throws IOException {
        Map<String, Object> map = new HashMap();
        String isSuccess = this.bookService.passBookByCode(bookCode);
        map.put("tip", isSuccess);
        return map;
    }

    @RequestMapping({"exportBook.do"})
    public void exportBook(HttpServletResponse response) throws Exception {
        List<Book> list = this.bookService.getList();
        String[] heads = new String[]{"序号", "书籍代码", "书籍名称", "数量", "可借数量", "书架", "状态"};
        List<String[]> data = new LinkedList();

        for(int i = 0; i < list.size(); ++i) {
            Book entity = (Book)list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getBookId(), entity.getBookName(), String.valueOf(entity.getBookNumber()), String.valueOf(entity.getLendNumber()), entity.getBookLocation(), String.valueOf(entity.getBookState())};
            data.add(temp);
        }

        ExcelParam param = (new Builder("书籍列表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }
}
