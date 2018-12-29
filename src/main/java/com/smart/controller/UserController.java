package com.smart.controller;

import com.github.pagehelper.PageInfo;
import com.smart.bean.*;
import com.smart.redis.ExcelParam;
import com.smart.redis.ExcelUtil;
import com.smart.redis.NetworkUtil;
import com.smart.service.SystemLogoutFilter;
import com.smart.service.UserService;
import com.smart.service.UtilService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;

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
    public Map<String,Object> Login(String geNumber, String password, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        //主体,当前状态为没有认证的状态“未认证”
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        Serializable sessionId = session.getId();
        System.out.println(sessionId.toString());
        // 登录后存放进shiro token
        UsernamePasswordToken token=new UsernamePasswordToken(geNumber,password);
        //token.setRememberMe(true);
        //登录方法（认证是否通过）
        //使用subject调用securityManager,安全管理器调用Realm
        try {
            //利用异常操作
            //需要开始调用到Realm中
            //System.out.println("========================================");
            //System.out.println("1、进入认证方法");
            subject.login(token);
            //System.out.println("登录完成");
        } catch (Exception e) {
            map.put("tip","error");
            return map;
        }
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setIpNumber(NetworkUtil.getIpAddr(request));
        accessRecord.setGeNumber(geNumber);
        accessRecord.setSessionId(sessionId.toString());
        utilService.addAccess(accessRecord);
        map.put("tip","success");
        return map;
    }



    @RequestMapping(value="/userList.do")
    public ModelAndView userList() {
        return new ModelAndView("userList");
    }

    @RequestMapping(value="/userInform.do")
    public ModelAndView userInform() {
        return new ModelAndView("userInform");
    }


    @RequestMapping(value="/error.do")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    @RequestMapping(value="/inform")
    public ModelAndView inform(String id) {
        System.out.println(id);

        ModelAndView modelAndView = new ModelAndView("inform");
        modelAndView.addObject("inform",userService.getInformById(Integer.parseInt(id)));
        return modelAndView;
    }
    //查询用户列表
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

    //查询书籍列表
    @RequestMapping(value = "/listInform.do")
    @ResponseBody
    public Map<String,Object> listInform(Inform inform, Integer pageNum, Integer pageSize)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        inform.setGeNumber(geNumber);
        //获取分页信息
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 15;
        }
        if (inform.getTitle() != null) {
            inform.setTitle("%" + inform.getTitle() + "%");
        }
        PageInfo<Inform> page = userService.pageInform(inform, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    //删除
    @RequestMapping(value = "/Inform.do", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> delInform(@RequestBody Inform inform) throws IOException {
            System.out.println(inform.getId());

        Map<String,Object> map = new HashMap<String,Object>();
        boolean isSuccess = userService.delInform(inform.getId());
        if(isSuccess){
            map.put("tip", "success");
        }
        else{
            map.put("tip", "error");
        }
        return map;
    }

    @RequestMapping(value = "exportUser.do")
    public void exportUser(HttpServletResponse response) throws Exception {
        List<User> list = userService.getUserList();
        String[] heads = {"序号", "工号", "姓名", "角色", "状态", "创建时间", "修改时间","电话","邮件"};
        List<String[]> data = new LinkedList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            User entity = list.get(i);
            String[] temp = new String[9];
            temp[0] = String.valueOf(i+1);
            temp[1] = entity.getGeNumber();
            temp[2] = entity.getGeName();
            temp[3] = String.valueOf(entity.getRole());
            temp[4] = String.valueOf(entity.getUserState());
            temp[5] = entity.getCreateTime();
            temp[6] = entity.getUpdateTime();
            temp[7] = entity.getPhone();
            temp[8] = entity.getEmail();
            data.add(temp);
        }
        ExcelParam param = new ExcelParam.Builder("用户列表").headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }

    @RequestMapping(value = "exportAllege.do")
    public void exportAllege(String begin,String end,String userState,HttpServletResponse response) throws Exception {
        //String u = java.net.URLDecoder.decode("userState","utf-8");
        String u = new String(userState.getBytes("ISO-8859-1"), "UTF-8");
        System.out.println(begin+","+end+","+u);
        List<Record> list = userService.getRecordByTime(begin,end,u);
        String[] heads = {"序号", "书籍代码", "姓名", "工号", "书名", "状态", "申请时间","借阅日期","归还日期","描述"};
        List<String[]> data = new LinkedList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            Record entity = list.get(i);
            String[] temp = new String[10];
            temp[0] = String.valueOf(i+1);
            temp[1] = entity.getBookId();
            temp[2] = entity.getGeName();
            temp[3] = entity.getGeNumber();
            temp[4] = entity.getBookName();
            temp[5] = entity.getState();
            temp[6] = entity.getApplyTime();
            temp[7] = entity.getLendTime();
            temp[8] = entity.getBackTime();
            temp[9] = entity.getDescription();
            data.add(temp);
        }
        ExcelParam param = new ExcelParam.Builder("借阅记录表").headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }
}
