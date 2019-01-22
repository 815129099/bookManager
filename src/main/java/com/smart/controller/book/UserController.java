
package com.smart.controller.book;

import com.github.pagehelper.PageInfo;
import com.smart.bean.AccessRecord;
import com.smart.bean.Inform;
import com.smart.bean.Record;
import com.smart.bean.User;
import com.smart.redis.ExcelParam;
import com.smart.redis.ExcelUtil;
import com.smart.redis.NetworkUtil;
import com.smart.redis.ExcelParam.Builder;
import com.smart.service.UserService;
import com.smart.service.UtilService;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
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
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UtilService utilService;

    public UserController() {
    }

    @RequestMapping({"/login.do"})
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @RequestMapping({"/testLogin.do"})
    public ModelAndView testLogin() {
        return new ModelAndView("login");
    }

    @RequestMapping({"/detail.do"})
    public ModelAndView detail() {
        return new ModelAndView("book/detail");
    }

    @RequestMapping({"/UserType.do"})
    @ResponseBody
    public Map<String, Object> Login(String geNumber, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        Serializable sessionId = session.getId();
        System.out.println(sessionId.toString());
        UsernamePasswordToken token = new UsernamePasswordToken(geNumber, password);

        try {
            subject.login(token);
        } catch (Exception var10) {
            map.put("tip", "error");
            return map;
        }

        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setIpNumber(NetworkUtil.getIpAddr(request));
        accessRecord.setGeNumber(geNumber);
        accessRecord.setSessionId(sessionId.toString());
        this.utilService.addAccess(accessRecord);
        map.put("tip", "success");
        return map;
    }

    @RequestMapping({"/userList.do"})
    public ModelAndView userList() {
        return new ModelAndView("book/userList");
    }

    @RequestMapping({"/userInform.do"})
    public ModelAndView userInform() {
        return new ModelAndView("book/userInform");
    }

    @RequestMapping({"/error.do"})
    public ModelAndView error() {
        return new ModelAndView("book/error");
    }

    @RequestMapping({"/inform"})
    public ModelAndView inform(String id) {
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView("book/inform");
        modelAndView.addObject("inform", this.userService.getInformById(Integer.parseInt(id)));
        return modelAndView;
    }

    @RequestMapping({"/listUser.do"})
    @ResponseBody
    public Map<String, Object> listUser(User user, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (user.getGeName() != null) {
            user.setGeName("%" + user.getGeName() + "%");
        }

        if (user.getGeNumber() != null) {
            user.setGeNumber("%" + user.getGeNumber() + "%");
        }

        PageInfo<User> page = this.userService.pageUser(user, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping(
            value = {"/User.do"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, Object> addUser(User user) throws IOException {
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.addUser(user);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/isNumberExist.do"})
    @ResponseBody
    public Map<String, Object> isNumberExist(String geNumber) throws Exception {
        Map<String, Object> result = new HashMap();
        boolean isSuccess = this.userService.isNumberExist(geNumber);
        if (isSuccess) {
            result.put("tip", "success");
        } else {
            result.put("tip", "error");
        }

        return result;
    }

    @RequestMapping(
            value = {"/User.do"},
            method = {RequestMethod.PUT}
    )
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody User user) throws IOException {
        if (!StringUtils.isEmpty(user)) {
            System.out.print(user.getPassword() + "<" + user.getPhone() + "," + user.getGeNumber());
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.updateUser(user);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping(
            value = {"/User.do"},
            method = {RequestMethod.DELETE}
    )
    @ResponseBody
    public Map<String, Object> delUser(@RequestBody User user) throws IOException {
        if (user.getGeNumber() != "") {
            System.out.println(user.getGeNumber());
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.delUser(user.getGeNumber());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/password.do"})
    @ResponseBody
    public Map<String, Object> updatePassword(String password, String newPassword) throws IOException {
        Map<String, Object> map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        System.out.println(password + "," + newPassword + "," + geNumber);
        boolean isSuccess = this.userService.updatePassword(geNumber, password, newPassword);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/lockUser.do"})
    @ResponseBody
    public Map<String, Object> lockUser(int[] arr) throws IOException {
        int[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            System.out.println(id);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.lockUser(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/clearUser.do"})
    @ResponseBody
    public Map<String, Object> clearUser(int[] arr) throws IOException {
        int[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            System.out.println(id);
        }

        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.clearUser(arr);
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"/listInform.do"})
    @ResponseBody
    public Map<String, Object> listInform(Inform inform, Integer pageNum, Integer pageSize) throws Exception {
        Map<String, Object> map = new HashMap();
        Subject subject = SecurityUtils.getSubject();
        String geNumber = (String)subject.getPrincipal();
        inform.setGeNumber(geNumber);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

        if (pageSize == null) {
            pageSize = 15;
        }

        if (inform.getTitle() != null) {
            inform.setTitle("%" + inform.getTitle() + "%");
        }

        PageInfo<Inform> page = this.userService.pageInform(inform, pageNum, pageSize);
        map.put("page", page);
        return map;
    }

    @RequestMapping(
            value = {"/Inform.do"},
            method = {RequestMethod.DELETE}
    )
    @ResponseBody
    public Map<String, Object> delInform(@RequestBody Inform inform) throws IOException {
        System.out.println(inform.getId());
        Map<String, Object> map = new HashMap();
        boolean isSuccess = this.userService.delInform(inform.getId());
        if (isSuccess) {
            map.put("tip", "success");
        } else {
            map.put("tip", "error");
        }

        return map;
    }

    @RequestMapping({"exportUser.do"})
    public void exportUser(HttpServletResponse response) throws Exception {
        List<User> list = this.userService.getUserList();
        String[] heads = new String[]{"序号", "工号", "姓名", "角色", "状态", "创建时间", "修改时间", "电话", "邮件"};
        List<String[]> data = new LinkedList();

        for(int i = 0; i < list.size(); ++i) {
            User entity = (User)list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getGeNumber(), entity.getGeName(), String.valueOf(entity.getRole()), String.valueOf(entity.getUserState()), entity.getCreateTime(), entity.getUpdateTime(), entity.getPhone(), entity.getEmail()};
            data.add(temp);
        }

        ExcelParam param = (new Builder("用户列表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }

    @RequestMapping({"exportAllege.do"})
    public void exportAllege(String begin, String end, String userState, HttpServletResponse response) throws Exception {
        String u = new String(userState.getBytes("ISO-8859-1"), "UTF-8");
        System.out.println(begin + "," + end + "," + u);
        List<Record> list = this.userService.getRecordByTime(begin, end, u);
        String[] heads = new String[]{"序号", "书籍代码", "姓名", "工号", "书名", "状态", "申请时间", "借阅日期", "归还日期", "描述"};
        List<String[]> data = new LinkedList();

        for(int i = 0; i < list.size(); ++i) {
            Record entity = (Record)list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getBookId(), entity.getGeName(), entity.getGeNumber(), entity.getBookName(), entity.getState(), entity.getApplyTime(), entity.getLendTime(), entity.getBackTime(), entity.getDescription()};
            data.add(temp);
        }

        ExcelParam param = (new Builder("借阅记录表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }

    @RequestMapping({"/getMoney.do"})
    @ResponseBody
    public Map<String, Object> getMoney(String geNumber) throws IOException {
        System.out.println(geNumber);
        Map<String, Object> map = new HashMap();
        User user = this.userService.getUserByGrNumber(geNumber);
        map.put("money", user.getUserMoney());
        return map;
    }
}
