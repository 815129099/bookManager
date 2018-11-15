package com.smart.controller;

import com.github.pagehelper.PageInfo;
import com.smart.bean.Book;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login.do")
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @RequestMapping(value="/detail.do")
    public ModelAndView detail() {
        return new ModelAndView("detail");
    }

    @RequestMapping(value = "/UserType.do")
    @ResponseBody
    public Map<String,Object> Login(String geNumber, String password){
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println("SESSION ID = " + SecurityUtils.getSubject().getSession().getId());
        System.out.println("用户名：" + SecurityUtils.getSubject().getPrincipal());
        System.out.println("HOST：" + SecurityUtils.getSubject().getSession().getHost());
        System.out.println("TIMEOUT ：" + SecurityUtils.getSubject().getSession().getTimeout());
        System.out.println("START：" + SecurityUtils.getSubject().getSession().getStartTimestamp());
        System.out.println("LAST：" + SecurityUtils.getSubject().getSession().getLastAccessTime());
        //主体,当前状态为没有认证的状态“未认证”
        Subject subject = SecurityUtils.getSubject();
        // 登录后存放进shiro token
        UsernamePasswordToken token=new UsernamePasswordToken(geNumber,password);
        //token.setRememberMe(true);
        //登录方法（认证是否通过）
        //使用subject调用securityManager,安全管理器调用Realm
        try {
            //利用异常操作
            //需要开始调用到Realm中
            System.out.println("========================================");
            System.out.println("1、进入认证方法");
            subject.login(token);
            System.out.println("登录完成");
        } catch (Exception e) {
            map.put("tip","error");
            return map;
        }
        map.put("tip","success");
        return map;
    }



    @RequestMapping(value="/userList.do")
    public ModelAndView userList() {
        return new ModelAndView("userList");
    }


    @RequestMapping(value="/error.do")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    //查询书籍列表
    @RequestMapping(value = "/listUser.do")
    @ResponseBody
    public Map<String,Object> listUser(User user, Integer pageNum, Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        //获取分页信息
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (user.getGeName() != null) {
            user.setGeName("%" + user.getGeName() + "%");
        }
        if(user.getGeNumber() != null){
            user.setGeNumber("%" + user.getGeNumber() + "%");
        }
        PageInfo<User> page = userService.pageUser(user, pageNum, pageSize);
        map.put("page", page);

        return map;
    }

    //添加用户
    @RequestMapping(value = "/User.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addUser(User user) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.addUser(user);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    // 判断工号是否已存在
    @RequestMapping("/isNumberExist.do")
    @ResponseBody
    public Map<String, Object> isNumberExist(String geNumber) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isSuccess = userService.isNumberExist(geNumber);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }
        return result;
    }

    //修改
    @RequestMapping(value = "/User.do", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updateUser(@RequestBody User user) throws IOException {
        if(!StringUtils.isEmpty(user)){
            System.out.print(user.getPassword()+"<"+user.getPhone()+","+user.getGeNumber());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.updateUser(user);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    //删除
    @RequestMapping(value = "/User.do", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delUser(@RequestBody User user) throws IOException {
        if(user.getGeNumber()!=""){
            System.out.println(user.getGeNumber());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.delUser(user.getGeNumber());
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    //修改密码
    @RequestMapping(value = "/password.do")
    @ResponseBody
    public Map<String,Object> updatePassword(String password,String newPassword) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        System.out.println(password+","+newPassword+","+geNumber);
        boolean isSuccess = userService.updatePassword(geNumber,password,newPassword);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    //锁定用户
    @RequestMapping(value = "/lockUser.do")
    @ResponseBody
    public Map<String,Object> lockUser(int[] arr) throws IOException {
        for (int id:arr) {
            System.out.println(id);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.lockUser(arr);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }


    //解锁用户
    @RequestMapping(value = "/clearUser.do")
    @ResponseBody
    public Map<String,Object> clearUser(int[] arr) throws IOException {
        for (int id:arr) {
            System.out.println(id);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.clearUser(arr);
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }
}
