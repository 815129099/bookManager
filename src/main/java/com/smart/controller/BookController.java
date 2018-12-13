package com.smart.controller;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.bean.muFile;
import com.smart.redis.ExcelParam;
import com.smart.redis.ExcelUtil;
import com.smart.service.BookService;
import com.smart.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    com.smart.redis.JedisClient JedisClient;





    @RequestMapping(value="/index.do")
    public ModelAndView index() {
        return new ModelAndView("index1");
    }

    @RequestMapping(value="/maSystem.do")
    public ModelAndView maSystem() {
        return new ModelAndView("maSystem");
    }

    @RequestMapping(value="/meList.do")
    public ModelAndView meList() {
        return new ModelAndView("meList");
    }

    @RequestMapping(value="/allegeList.do")
    public ModelAndView allegeList(String geNumber,String bookId)
    {
        ModelAndView modelAndView = new ModelAndView("allegeList");
        if(!StringUtils.isEmpty(geNumber)&&StringUtils.isEmpty(bookId)){
            System.out.println(geNumber);
            modelAndView.addObject("geNumber",geNumber);
        }else if(StringUtils.isEmpty(geNumber)&&!StringUtils.isEmpty(bookId)){
            System.out.println(bookId);
            modelAndView.addObject("bookId",bookId);
        }
        return modelAndView;
    }




    // 用户列表
    @RequestMapping("/bookList.do")
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
    public Map<String,Object> listBook(Book book, Integer pageNum, Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
            //获取分页信息
            if (pageNum == null || pageNum == 0) {
                pageNum = 1;
            }
            if (pageSize == null) {
                pageSize = 15;
            }
            if (book.getBookName() != null) {
                book.setBookName(book.getBookName() + "%");
            }
            if(book.getBookLocation() != null){
                book.setBookLocation("%" + book.getBookLocation() + "%");
            }
            PageInfo<Book> page = bookService.pageBook(book, pageNum, pageSize);
            map.put("page", page);

        return map;
    }

    //查询借阅记录列表
    @RequestMapping("/listRecord.do")
    @ResponseBody
    public Map<String,Object> listRecord(Record record, Integer pageNum, Integer pageSize)throws Exception{
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        Map<String,Object> map = new HashMap<String,Object>();
        //获取分页信息
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        Set<String> set = userService.findRoles(geNumber);
        String role = "";
        if(!set.isEmpty()){
            role = set.iterator().next();
            System.out.println(role);
        }

        if(role.equals("admin")){
            if(record.getGeNumber() != null){
                record.setGeNumber("%" + record.getGeNumber() + "%");
            }
            PageInfo<Record> page = bookService.pageRecord(record, pageNum, pageSize);
            map.put("page", page);
        }else {
            PageInfo<Record> page = bookService.pageRecord1(record, pageNum, pageSize,geNumber);
            map.put("page", page);
        }
        return map;
    }
    //添加书籍
    @RequestMapping(value = "/Book.do", method = RequestMethod.POST)
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

    //登记，添加记录
    @RequestMapping(value = "/Record.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkBook(Record record) throws IOException {
        System.out.println(record.getGeName());
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        System.out.println("用户"+geNumber);
        //如果没有工号则获取当前用户名
        if(record.getGeNumber()==null || record.getGeNumber()==""){

            record.setGeNumber(geNumber);
            System.out.println(record.getGeNumber()+"asdasdasd");
        }else{
            System.out.println(record.getGeNumber()+"123123123");
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

    // 还书登记 修改记录
    @RequestMapping(value = "/Record.do", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> backBook(@RequestBody Record record) throws Exception {
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

    //删除记录
    @RequestMapping(value = "/Record.do", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delRecord(@RequestBody Record record) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.delRecord(record.getId());
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    //删除书籍
    @RequestMapping(value = "/Book.do", method = RequestMethod.DELETE)
    @ResponseBody
        public Map<String,Object> delBook(@RequestBody Book book) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
            boolean isSuccess = bookService.delBook(book.getBookId());
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }


    //通过申请
    @RequestMapping(value = "/pass.do")
    @ResponseBody
    public Map<String,Object> pass(int[] arr) throws IOException {
        for (int id:arr) {
            System.out.println(id);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.pass(arr);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }


    //退回申请
    @RequestMapping(value = "/back.do")
    @ResponseBody
    public Map<String,Object> back(int[] arr) throws IOException {
        for (int id:arr) {
            System.out.println(id);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = bookService.back(arr);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }






    @RequestMapping(value="/upload.do",method=RequestMethod.POST)
    public String upload(HttpServletRequest request,
                         @ModelAttribute muFile file, Model model) throws Exception {

        System.out.println(file.getDescription());
        //如果文件不为空，写入上传路径
        if(!file.getFile().isEmpty()) {
            //上传文件路径
            String path = "F:/FileDownloads/"+file.getFile().getOriginalFilename();
            //上传文件名
            String filename = file.getFile().getOriginalFilename();
            File filepath = new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.getFile().transferTo(new File(path + File.separator + filename));
            JedisClient.setFile(file.getDescription(),path);
            model.addAttribute("muFile",file);
            return "userinfo";
        } else {
            return "error";
        }

    }

    @RequestMapping(value="/download.do")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("filename") String filename,
                                           Model model)throws Exception {
        //下载文件路径
        String path = "F:/FileDownloads/"+filename;
        File file = new File(path + File.separator + filename);

        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "exportBook.do")
    public void exportBook(HttpServletResponse response) throws Exception {
        List<Book> list = bookService.getList();
        String[] heads = {"序号", "书籍代码", "书籍名称", "数量", "可借数量", "书架", "状态"};
        List<String[]> data = new LinkedList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            Book entity = list.get(i);
            String[] temp = new String[7];
            temp[0] = String.valueOf(i+1);
            temp[1] = entity.getBookId();
            temp[2] = entity.getBookName();
            temp[3] = String.valueOf(entity.getBookNumber());
            temp[4] = String.valueOf(entity.getLendNumber());
            temp[5] = entity.getBookLocation();
            temp[6] = String.valueOf(entity.getBookState());
            data.add(temp);
        }
        ExcelParam param = new ExcelParam.Builder("书籍列表").headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }



}
